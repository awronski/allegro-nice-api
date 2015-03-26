package com.apwglobal.nice.auction;

import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.ItemPriceType;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;
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
            List<ItemPriceStruct> item = s.getItemPrice().getItem();
            ItemPriceStruct itemPriceStruct;

            if (item.size() != 1 || (itemPriceStruct = item.get(0)).getPriceType() != ItemPriceType.BUY_NOW.getType()) {
                throw new IllegalArgumentException("Cannot support auction with sale diffrent than buy now");
            }

            return new Auction.Builder()
                    .id(s.getItemId())
                    .title(s.getItemTitle())
                    .thumbnailUrl(s.getItemThumbnailUrl())
                    .startQuantity(s.getItemStartQuantity())
                    .soldQuantity(s.getItemSoldQuantity())
                    .quantityType(s.getItemQuantityType())
                    .startTime(s.getItemStartTime())
                    .endTime(s.getItemEndTime())
                    .biddersCounter(s.getItemBiddersCounter())
                    .categoryId(s.getItemCategoryId())
                    .watchersCounter(s.getItemWatchersCounter())
                    .viewsCounter(s.getItemViewsCounter())
                    .note(s.getItemNote())
                    .special(s.getItemSpecialInfo())
                    .shop(s.getItemShopInfo())
                    .payu(s.getItemPayuInfo())
                    .price(itemPriceStruct.getPriceValue())
                    .priceType(itemPriceStruct.getPriceType())
                    .build();
        }

        @Override
        protected long getItemId(Auction auction) {
            this.startingPoint += 1;
            return startingPoint;
        }

    }
}
