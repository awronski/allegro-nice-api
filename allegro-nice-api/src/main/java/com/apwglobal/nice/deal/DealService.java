package com.apwglobal.nice.deal;

import com.apwglobal.nice.conv.DealConv;
import com.apwglobal.nice.conv.PaymentConv;
import com.apwglobal.nice.country.InfoService;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.DealType;
import com.apwglobal.nice.domain.Payment;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;
import rx.Observable;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class DealService extends AbstractService {

    private InfoService infoService;

    public DealService(ServicePort allegro, Credentials cred, Configuration conf, InfoService countryService) {
        super(allegro, cred, conf);
        this.infoService = countryService;
    }

    public Observable<Deal> getDeals(String session, long startingPoint) {
        return Observable.from(() -> new DealsIterator(session, startingPoint));
    }

    public Observable<Payment> getPayments(String session, Observable<Deal> deals) {
        Observable<Long> transactionsIds = deals
                .filter(d -> d.getTransactionId().isPresent())
                .filter(d -> d.getDealType().equals(DealType.PAYMENT))
                .map(Deal::getTransactionId)
                .map(Optional::get)
                .distinct();

        return transactionsIds
                .buffer(25)
                .flatMap(l -> Observable.from(getPostBuyFormsDataForSellers(session, l)))
                .map(f -> PaymentConv.convert(f, infoService.getCountries()));
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
                    .map(DealConv::convert)
                    .collect(toList());
        }

        @Override
        protected long getItemId(Deal deal) {
            return deal.getEventId();
        }

    }
}
