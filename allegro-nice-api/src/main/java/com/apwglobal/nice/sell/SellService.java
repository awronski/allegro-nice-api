package com.apwglobal.nice.sell;

import com.apwglobal.bd.BD;
import com.apwglobal.nice.conv.AuctionFieldConv;
import com.apwglobal.nice.conv.FormFieldConv;
import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.exception.RestApiException;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.rest.RestApiSession;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.apwglobal.nice.util.ClientExecuteUtil;
import com.apwglobal.nice.util.RestCommandBuilder;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.jetbrains.annotations.NotNull;
import pl.allegro.webapi.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.apwglobal.nice.exception.AllegroExecutor.execute;
import static java.util.stream.Collectors.toList;

public class SellService extends AbstractService {

    private static final String[] SALES_CONDITIONS_PATHS = new String[] {
            "/after-sales-service-conditions/implied-warranties",
            "/after-sales-service-conditions/return-policies",
            "/after-sales-service-conditions/warranties"
    };

    public SellService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,782#method-output
     */
    public List<FormField> getSellFormFields(int categoryId) {
        DoGetSellFormFieldsForCategoryRequest req = new DoGetSellFormFieldsForCategoryRequest(cred.getKey(), conf.getCountryId(), categoryId);
        DoGetSellFormFieldsForCategoryResponse res = execute(() -> allegro.doGetSellFormFieldsForCategory(req));
        return res.getSellFormFieldsForCategory().getSellFormFieldsList().getItem()
                .stream()
                .map(FormFieldConv::convert)
                .collect(toList());
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,41#method-output
     */
    public NewAuctionPrice checkNewAuction(List<AuctionField> fields, String session) {
        DoCheckNewAuctionExtRequest req = new DoCheckNewAuctionExtRequest();
        req.setSessionHandle(session);
        req.setFields(AuctionFieldConv.convert(fields));
        DoCheckNewAuctionExtResponse res = execute(() -> allegro.doCheckNewAuctionExt(req));

        return new NewAuctionPrice.Builder()
                .price(res.getItemPrice())
                .priceDesc(res.getItemPriceDesc())
                .allegroStandard(res.getItemIsAllegroStandard())
                .build();
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,113#method-output
     */
    public CreatedAuction createNewAuction(@NotNull NewAuction newAuction, String session) {
        DoNewAuctionExtRequest req = createDoNewAuctionExtRequest(newAuction, session);
        DoNewAuctionExtResponse res = execute(() -> allegro.doNewAuctionExt(req));
        return new CreatedAuction.Builder()
                .itemId(res.getItemId())
                .info(res.getItemInfo())
                .allegroStandard(res.getItemIsAllegroStandard())
                .build();
    }

    private DoNewAuctionExtRequest createDoNewAuctionExtRequest(NewAuction newAuction, String session) {
        SalesConditions cond = newAuction.getSalesConditions();
        AfterSalesServiceConditionsStruct salesCondition = new AfterSalesServiceConditionsStruct(cond.getImpliedWarranty(), cond.getReturnPolicy(), cond.getWarranty());

        DoNewAuctionExtRequest req = new DoNewAuctionExtRequest();
        req.setAfterSalesServiceConditions(salesCondition);
        req.setSessionHandle(session);
        req.setFields(AuctionFieldConv.convert(newAuction.getFields()));
        req.setLocalId((int) (Math.random() * Integer.MAX_VALUE));
        return req;
    }

    public ChangedQty changeAuctionQty(String session, long itemId, int newQty) {
        DoChangeQuantityItemRequest req = new DoChangeQuantityItemRequest(session, itemId, newQty);
        DoChangeQuantityItemResponse res = execute(() -> allegro.doChangeQuantityItem(req));

        return new ChangedQty.Builder()
                .itemId(res.getItemId())
                .left(res.getItemQuantityLeft())
                .sold(res.getItemQuantitySold())
                .info(res.getItemInfo())
                .build();
    }

    /**
     * https://developer.allegroapi.io/documentation/#!/default/put_offers_offerId_change_price_commands_commandId
     */
    public ChangedPrice changeAuctionPrice(RestApiSession session, long itemId, double newPrice) {
        String price = String.valueOf(new BD(newPrice).floatValue(2));
        String input = String.format("{ \"input\": { \"buyNowPrice\": { \"amount\": \"%s\", \"currency\": \"PLN\" } } }", price);
        String path = String.format("/offers/%d/change-price-commands/%s", itemId, UUID.randomUUID());

        HttpPut put = new RestCommandBuilder()
                .path(path)
                .entity(input)
                .addHeader("Accept", "application/vnd.allegro.public.v1+json")
                .addHeader("Content-Type", "application/vnd.allegro.public.v1+json")
                .addHeader("Authorization", "Bearer " + session.getAccessToken())
                .addHeader("Api-Key", cred.getRestClientApiKey())
                .buildPut();

        String response = ClientExecuteUtil.execute(put);
        JsonElement element = new JsonParser().parse(response);
        JsonArray errors = element.getAsJsonObject().getAsJsonArray("errors");
        if (errors == null) {
            String info = element.getAsJsonObject().getAsJsonObject("output").get("status").getAsString();
            return new ChangedPrice.Builder().info(info).itemId(itemId).build();
        } else {
            throw new RestApiException(errors.toString());
        }
    }

    public List<String> getSalesConditions(RestApiSession session, long clientId) {
        return Arrays.stream(SALES_CONDITIONS_PATHS)
                .map(path -> retriveConditions(session, clientId, path))
                .collect(toList());
    }

    private String retriveConditions(RestApiSession session, long clientId, String path) {
        HttpGet get = new RestCommandBuilder()
                .path(path)
                .addParam("sellerId", String.valueOf(clientId))
                .addHeader("Accept", "application/vnd.allegro.public.v1+json")
                .addHeader("Content-Type", "application/vnd.allegro.public.v1+json")
                .addHeader("Authorization", "Bearer " + session.getAccessToken())
                .addHeader("Api-Key", cred.getRestClientApiKey())
                .buildGet();

        return ClientExecuteUtil.execute(get);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,623#method-output
     */
    public List<FinishAuctionFailure> finishAuctions(String session, List<Long> itemsIds) {
        return Lists.partition(itemsIds, 25)
                .stream()
                .flatMap(l -> finish(session, l)
                        .stream())
                .map(f -> new FinishAuctionFailure.Builder()
                        .itemId(f.getFinishItemId())
                        .errorCode(f.getFinishErrorCode())
                        .errorMessage(f.getFinishErrorMessage())
                        .build()
                )
                .collect(toList());
    }

    private List<FinishFailureStruct> finish(String session, List<Long> ids) {
        DoFinishItemsRequest req = new DoFinishItemsRequest(session, getItemsToFinish(ids));
        return execute(() -> allegro.doFinishItems(req)).getFinishItemsFailed().getItem();
    }

    private ArrayOfFinishitemsstruct getItemsToFinish(List<Long> ids) {
        return new ArrayOfFinishitemsstruct(
                ids
                        .stream()
                        .map(id -> new FinishItemsStruct(id, 0, null))
                        .collect(toList())
        );
    }
}
