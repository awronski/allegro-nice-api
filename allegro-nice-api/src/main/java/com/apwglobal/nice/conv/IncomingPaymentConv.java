package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.IncomingPayment;
import pl.allegro.webapi.UserIncomingPaymentStruct;

public class IncomingPaymentConv {

    public static IncomingPayment convert(long sellerId, UserIncomingPaymentStruct s) {
        return new IncomingPayment.Builder()
                .amount(s.getPayTransAmount())
                .buyerId(s.getPayTransBuyerId())
                .sellerId(sellerId)
                .incomplete(s.getPayTransIncomplete())
                .itemsCounter(s.getPayTransCount())
                .status(s.getPayTransStatus())
                .mainTransactionId(s.getPayTransMainId())
                .postageAmount(s.getPayTransPostageAmount())
                .price(s.getPayTransPrice())
                .receiveDate(s.getPayTransRecvDate())
                .transactionId(s.getPayTransId())
                .build();
    }

}
