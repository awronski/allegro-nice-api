package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Deal;
import pl.allegro.webapi.SiteJournalDealsStruct;

public class DealConv {

    public static Deal convert(SiteJournalDealsStruct d) {
        return new Deal.Builder()
                .buyerId(d.getDealBuyerId())
                .eventId(d.getDealEventId())
                .eventTime(d.getDealEventTime())
                .id(d.getDealId())
                .itemId(d.getDealItemId())
                .quantity(d.getDealQuantity())
                .sellerId(d.getDealSellerId())
                .transactionId(d.getDealTransactionId())
                .dealType(d.getDealEventType())
                .build();
    }
}
