package com.apwglobal.nice.deal;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.DoGetSiteJournalDealsRequest;
import pl.allegro.webapi.ServicePort;
import pl.allegro.webapi.SiteJournalDealsStruct;
import rx.Observable;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DealService extends AbstractService {

    public DealService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public Observable<Deal> getDeals(String session, long startingPoint) {
        return Observable.from(() -> new DealsIterator(session, startingPoint));
    }

    private class DealsIterator extends AbstractAllegroIterator<Deal> {

        public DealsIterator(String session, long startingPoint) {
            super(session, startingPoint);
        }

        @Override
        protected List<Deal> doFetch() {
            DoGetSiteJournalDealsRequest request = new DoGetSiteJournalDealsRequest(session, startingPoint);
            return AllegroExecutor.execute(() -> allegro.doGetSiteJournalDeals(request))
                    .getSiteJournalDeals()
                    .getItem()
                    .stream()
                    .map(this::createDeal)
                    .collect(toList());
        }

        private Deal createDeal(SiteJournalDealsStruct d) {
            return new Deal.Builder()
                    .dealBuyerId(d.getDealBuyerId())
                    .dealEventId(d.getDealEventId())
                    .dealEventTime(d.getDealEventTime())
                    .dealId(d.getDealId())
                    .dealItemId(d.getDealItemId())
                    .dealQuantity(d.getDealQuantity())
                    .dealSellerId(d.getDealSellerId())
                    .dealTransactionId(d.getDealTransactionId())
                    .dealType(d.getDealEventType())
                    .build();
        }

        @Override
        protected long getItemId(Deal deal) {
            return deal.getDealEventId();
        }

    }
}
