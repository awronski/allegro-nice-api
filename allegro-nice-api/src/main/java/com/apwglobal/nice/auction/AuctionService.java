package com.apwglobal.nice.auction;

import com.apwglobal.nice.conv.AuctionConv;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;
import rx.Observable;

import java.util.List;

import static com.apwglobal.nice.domain.ItemPriceType.BUY_NOW;
import static java.util.stream.Collectors.toList;

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
                    .collect(toList());
        }

        private Auction createAuction(SellItemStruct s) {
            List<ItemPriceStruct> item = s.getItemPrice().getItem();
            ItemPriceStruct itemPriceStruct;

            if (item.size() != 1 || (itemPriceStruct = item.get(0)).getPriceType() != BUY_NOW.getType()) {
                throw new IllegalArgumentException("Cannot support auction with sale diffrent than buy now");
            }

            return AuctionConv.convert(s, itemPriceStruct);
        }

        @Override
        protected long getItemId(Auction auction) {
            this.startingPoint += 1;
            return startingPoint;
        }

    }
}
