package com.apwglobal.nice.deal;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;
import rx.Observable;

import java.util.List;
import java.util.stream.Collectors;

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
            DoGetDealsRequest request = new DoGetDealsRequest(session, d.getDealItemId(), d.getDealBuyerId());
            DoGetDealsResponse response = AllegroExecutor.execute(() -> allegro.doGetDeals(request));

            double amountOriginal = response.getDealsList().getItem()
                    .stream()
                    .collect(Collectors.summingDouble(DealsStruct::getDealAmountOriginal));

            double amountDiscounted = response.getDealsList().getItem()
                    .stream()
                    .collect(Collectors.summingDouble(DealsStruct::getDealAmountDiscounted));

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
                    .dealAmountOriginal(amountOriginal)
                    .dealAmountDiscounted(amountDiscounted)
                    .build();
        }

        @Override
        protected long getItemId(Deal deal) {
            return deal.getDealEventId();
        }

    }
}
