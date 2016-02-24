package com.apwglobal.nice.sell;

import com.apwglobal.bd.BD;
import com.apwglobal.nice.conv.CategoryConv;
import com.apwglobal.nice.conv.FormFieldConv;
import com.apwglobal.nice.conv.AuctionFieldConv;
import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.google.common.collect.Lists;
import pl.allegro.webapi.*;

import java.util.List;

import static com.apwglobal.nice.exception.AllegroExecutor.execute;
import static java.util.stream.Collectors.toList;

public class SellService extends AbstractService {


    public SellService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,46#method-output
     */
    public List<Category> getCategories() {
        DoGetCatsDataRequest req = new DoGetCatsDataRequest(conf.getCountryId(), 0l, cred.getKey());
        DoGetCatsDataResponse res = execute(() -> allegro.doGetCatsData(req));
        return res.getCatsList().getItem()
                .stream()
                .map(CategoryConv::convert)
                .collect(toList());
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
    public CreatedAuction createNewAuction(List<AuctionField> fields, String session) {
        DoNewAuctionExtRequest req = createDoNewAuctionExtRequest(fields, session);
        DoNewAuctionExtResponse res = execute(() -> allegro.doNewAuctionExt(req));
        return new CreatedAuction.Builder()
                .itemId(res.getItemId())
                .info(res.getItemInfo())
                .allegroStandard(res.getItemIsAllegroStandard())
                .build();
    }

    private DoNewAuctionExtRequest createDoNewAuctionExtRequest(List<AuctionField> fields, String session) {
        DoNewAuctionExtRequest req = new DoNewAuctionExtRequest();
        req.setSessionHandle(session);
        req.setFields(AuctionFieldConv.convert(fields));
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

    public ChangedPrice changeAuctionPrice(String session, long itemId, double newPrice) {
        DoChangePriceItemRequest req = new DoChangePriceItemRequest(session, itemId, null, null, new BD(newPrice).floatValue(2));
        DoChangePriceItemResponse res = execute(() -> allegro.doChangePriceItem(req));

        return new ChangedPrice.Builder()
                .info(res.getItemInfo())
                .itemId(res.getItemId())
                .build();
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
