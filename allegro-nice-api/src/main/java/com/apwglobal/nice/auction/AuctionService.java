package com.apwglobal.nice.auction;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.DoGetMySellItemsRequest;
import pl.allegro.webapi.DoGetMySellItemsResponse;
import pl.allegro.webapi.SellItemStruct;
import pl.allegro.webapi.ServicePort;
import rx.Observable;

import java.util.List;
import java.util.stream.Collectors;

public class AuctionService extends AbstractService {

    public AuctionService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public Observable<Auction> getAuctions(String session) {
        return Observable.from(() -> new AuctionIterator(session, 0));
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
                    .collect(Collectors.toList());
        }

        private Auction createAuction(SellItemStruct s) {
            return new Auction.Builder()
                    .itemId(s.getItemId())
                    .itemTitle(s.getItemTitle())
                    .itemThumbnailUrl(s.getItemThumbnailUrl())
                    .itemStartQuantity(s.getItemStartQuantity())
                    .itemSoldQuantity(s.getItemSoldQuantity())
                    .itemQuantityType(s.getItemQuantityType())
                    .itemStartTime(s.getItemStartTime())
                    .itemEndTime(s.getItemEndTime())
                    .itemBiddersCounter(s.getItemBiddersCounter())
                    .itemCategoryId(s.getItemCategoryId())
                    .itemWatchersCounter(s.getItemWatchersCounter())
                    .itemViewsCounter(s.getItemViewsCounter())
                    .itemNote(s.getItemNote())
                    .special(s.getItemSpecialInfo())
                    .shop(s.getItemShopInfo())
                    .payu(s.getItemPayuInfo())
                    .build();
        }

        @Override
        protected long getItemId(Auction auction) {
            this.startingPoint += 1;
            return startingPoint;
        }

    }
}
