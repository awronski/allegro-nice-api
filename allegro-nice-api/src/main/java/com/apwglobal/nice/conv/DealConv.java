package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Deal;
import pl.allegro.webapi.SiteJournalDealsStruct;

public class DealConv {

    public static Deal convert(SiteJournalDealsStruct d) {
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
}
