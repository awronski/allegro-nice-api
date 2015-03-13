package com.apwglobal.nice.deal;

import com.apwglobal.nice.conv.PostBuyFormConv;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.DealType;
import com.apwglobal.nice.domain.PostBuyForm;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;
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

    public Observable<PostBuyForm> getPostBuyForms(String session, Observable<Deal> deals) {
        Observable<Long> transactionsIds = deals
                .filter(d -> d.getDealTransactionId() > 0)
                .filter(d -> d.getDealType().equals(DealType.PAYMENT))
                .map(Deal::getDealTransactionId)
                .distinct();

        return transactionsIds
                .buffer(25)
                .flatMap(l -> Observable.from(getPostBuyFormsDataForSellers(session, l)))
                .map(PostBuyFormConv::convert);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,703#method-output
     */
    private List<PostBuyFormDataStruct> getPostBuyFormsDataForSellers(String session, List<Long> transactionsIds) {
        DoGetPostBuyFormsDataForSellersRequest request = new DoGetPostBuyFormsDataForSellersRequest(session, new ArrayOfLong(transactionsIds));
        DoGetPostBuyFormsDataForSellersResponse response = AllegroExecutor.execute(() -> allegro.doGetPostBuyFormsDataForSellers(request));
        return response.getPostBuyFormData().getItem();
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,742#method-output
     */
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
