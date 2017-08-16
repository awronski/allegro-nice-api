package com.apwglobal.nice.payment;

import com.apwglobal.nice.conv.IncomingPaymentConv;
import com.apwglobal.nice.domain.IncomingPayment;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.DoGetMyIncomingPaymentsRequest;
import pl.allegro.webapi.DoGetMyIncomingPaymentsResponse;
import pl.allegro.webapi.ServicePort;
import rx.Observable;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class IncomingPaymentService extends AbstractService {

    public IncomingPaymentService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public Observable<IncomingPayment> getIncomingPayments(String session) {
        return Observable.from(() -> new IncomingPaymentIterator(session));
    };

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,85#method-output
     */
    private class IncomingPaymentIterator extends AbstractAllegroIterator<IncomingPayment> {
        IncomingPaymentIterator(String session) {
            super(session, 0);
        }

        @Override
        protected List<IncomingPayment> doFetch() {
            DoGetMyIncomingPaymentsRequest req = new DoGetMyIncomingPaymentsRequest();
            req.setSessionHandle(session);
            req.setTransOffset((int) startingPoint);
            DoGetMyIncomingPaymentsResponse res = AllegroExecutor.execute(() -> allegro.doGetMyIncomingPayments(req));

            return res.getPayTransIncome().getItem()
                    .stream()
                    .map(s -> IncomingPaymentConv.convert(cred.getClientId(), s))
                    .collect(toList());
        }

        @Override
        protected long getItemId(IncomingPayment auction) {
            this.startingPoint += 1;
            return startingPoint;
        }

    }

}
