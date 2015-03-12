package com.apwglobal.nice.deal;

import com.apwglobal.nice.domain.Address;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.DealType;
import com.apwglobal.nice.domain.PostBuyForm;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.google.common.collect.Lists;
import pl.allegro.webapi.*;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DealService extends AbstractService {

    public DealService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public Observable<Deal> getDeals(String session, long startingPoint) {
        return Observable.from(() -> new DealsIterator(session, startingPoint));
    }

    public List<PostBuyForm> getPostBuyForms(String session, List<Deal> deals) {
        Set<Long> transactionsIds = deals
                .stream()
                .filter(d -> d.getDealTransactionId() > 0)
                .filter(d -> d.getDealType().equals(DealType.PAYMENT))
                .map(Deal::getDealTransactionId)
                .collect(Collectors.toSet());

        return Lists.partition(new ArrayList<>(transactionsIds), 25)
                .stream()
                .flatMap(l -> getPostBuyFormsDataForSellers(session, l).stream())
                .map(this::createPostBuyFrom)
                .collect(Collectors.toList());
    }

    private PostBuyForm createPostBuyFrom(PostBuyFormDataStruct f) {
        PostBuyFormAddressStruct shipment = f.getPostBuyFormShipmentAddress();
        PostBuyFormAddressStruct invoice = f.getPostBuyFormInvoiceData();

        return new PostBuyForm.Builder()
                .buyerId(f.getPostBuyFormBuyerId())
                .email(f.getPostBuyFormBuyerEmail())
                .amount(f.getPostBuyFormAmount())
                .postageAmount(f.getPostBuyFormPostageAmount())
                .paymentAmount(f.getPostBuyFormPaymentAmount())
                .withInvoice(f.getPostBuyFormInvoiceOption())
                .msg(f.getPostBuyFormMsgToSeller())
                .payId(f.getPostBuyFormPayId())
                .payStatus(f.getPostBuyFormPayStatus())
                .shipmentId(f.getPostBuyFormShipmentId())
                .invoice(createAddress(invoice))
                .shipment(createAddress(shipment))
                .build();
    }

    private Address createAddress(PostBuyFormAddressStruct data) {
        return new Address.Builder()
                .fullname(data.getPostBuyFormAdrFullName())
                .company(data.getPostBuyFormAdrCompany())
                .street(data.getPostBuyFormAdrStreet())
                .code(data.getPostBuyFormAdrPostcode())
                .city(data.getPostBuyFormAdrCity())
                .countryId(data.getPostBuyFormAdrCountry())
                .phone(data.getPostBuyFormAdrPhone())
                .nip(data.getPostBuyFormAdrNip())
                .build();
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
