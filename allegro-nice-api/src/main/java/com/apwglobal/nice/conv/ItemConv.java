package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Item;
import pl.allegro.webapi.PostBuyFormItemStruct;

public class ItemConv {

    public static Item convert(PostBuyFormItemStruct s, long transactionId, long sellerId) {
        return new Item.Builder()
                .id(s.getPostBuyFormItId())
                .transactionId(transactionId)
                .sellerId(sellerId)
                .title(s.getPostBuyFormItTitle())
                .price(s.getPostBuyFormItPrice())
                .quantity(s.getPostBuyFormItQuantity())
                .amount(s.getPostBuyFormItAmount())
                .build();
    }

}
