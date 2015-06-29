package com.apwglobal.nice.auction;

import com.apwglobal.nice.conv.AuctionConv;
import com.apwglobal.nice.conv.AuctionFieldConv;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.ChangedAuctionInfo;
import com.apwglobal.nice.domain.NewAuctionField;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;
import rx.Observable;

import java.util.List;

import static com.apwglobal.nice.domain.ItemPriceType.BUY_NOW;
import static com.apwglobal.nice.exception.AllegroExecutor.execute;
import static java.util.stream.Collectors.toList;

public class AuctionService extends AbstractService {

    public AuctionService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public Observable<Auction> getAuctions(String session) {
        return Observable.from(() -> new AuctionIterator(session, 0));
    }

    public ChangedAuctionInfo changeAuctions(Long itemId, List<NewAuctionField> fieldsToModify, String session) {
        DoChangeItemFieldsRequest req = new DoChangeItemFieldsRequest();
        req.setItemId(itemId);
        req.setSessionId(session);
        req.setFieldsToModify(AuctionFieldConv.convert(fieldsToModify));

        DoChangeItemFieldsResponse res = execute(() -> allegro.doChangeItemFields(req));
        return convert(res);
    }

    private ChangedAuctionInfo convert(DoChangeItemFieldsResponse res) {
        ChangedItemStruct changedItem = res.getChangedItem();
        float surcharge = getAmountFromChangedItem(changedItem, "Dopłata za nowe opcje");
        float total = getAmountFromChangedItem(changedItem, "Suma");

        return new ChangedAuctionInfo.Builder()
                .itemId(changedItem.getItemId())
                .surchargeAmount(surcharge)
                .totalAmount(total)
                .build();
    }

    private Float getAmountFromChangedItem(ChangedItemStruct changedItem, String desc) {
        return changedItem.getItemSurcharge().getItem()
                .stream()
                .filter(i -> i.getSurchargeDescription().equals(desc))
                .findAny()
                .map(i -> i.getSurchargeAmount().getAmountValue())
                .orElse(0f);
    }

    private class AuctionIterator extends AbstractAllegroIterator<Auction> {
        public AuctionIterator(String session, long startingPoint) {
            super(session, startingPoint);
        }

        @Override
        protected List<Auction> doFetch() {
            DoGetMySellItemsRequest request = new DoGetMySellItemsRequest(session, null, null, null, null, null, 1000, (int) startingPoint);
            DoGetMySellItemsResponse response = AllegroExecutor.execute(() -> allegro.doGetMySellItems(request));

            return response.getSellItemsList().getItem()
                    .stream()
                    .map(this::createAuction)
                    .collect(toList());
        }

        private Auction createAuction(SellItemStruct s) {
            List<ItemPriceStruct> item = s.getItemPrice().getItem();
            ItemPriceStruct itemPriceStruct;

            if (item.size() != 1 || (itemPriceStruct = item.get(0)).getPriceType() != BUY_NOW.getType()) {
                throw new IllegalArgumentException("Cannot support auction with sale diffrent than buy now");
            }

            return AuctionConv.convert(s, itemPriceStruct, cred.getClientId());
        }

        @Override
        protected long getItemId(Auction auction) {
            this.startingPoint += 1;
            return startingPoint;
        }

    }
}
