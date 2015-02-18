package com.apwglobal.nice.deal;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.google.common.collect.Lists;
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

    public void fillBuyersForms(String session, List<Deal> deals) {
        List<Long> transactionsIds = deals
                .stream()
                .map(Deal::getDealTransactionId)
                .collect(Collectors.toList());

        List<PostBuyFormDataStruct> datas = Lists.partition(transactionsIds, 25)
                .stream()
                .flatMap(l -> getPostBuyFormsDataForSellers(session, l).stream())
                .collect(Collectors.toList());

        deals
                .stream()
                .forEach(d -> setPostBuyForm(datas, d));

    }

    private void setPostBuyForm(List<PostBuyFormDataStruct> forms, Deal deal) {
        deal.setPostBuyFormDataStruct(
                forms.stream()
                        .filter(f -> Long.valueOf(deal.getDealTransactionId()).equals(deal.dealTransactionId))
                        .findAny()
        );
    }

    private List<PostBuyFormDataStruct> getPostBuyFormsDataForSellers(String session, List<Long> transactionsIds) {
        DoGetPostBuyFormsDataForSellersRequest request = new DoGetPostBuyFormsDataForSellersRequest(session, new ArrayOfLong(transactionsIds));
        DoGetPostBuyFormsDataForSellersResponse response = AllegroExecutor.execute(() -> allegro.doGetPostBuyFormsDataForSellers(request));
        return response.getPostBuyFormData().getItem();
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
