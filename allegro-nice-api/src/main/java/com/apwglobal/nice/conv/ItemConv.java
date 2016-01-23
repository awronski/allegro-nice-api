package com.apwglobal.nice.conv;

import com.apwglobal.bd.BD;
import com.apwglobal.nice.domain.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.webapi.ArrayOfPostbuyformitemdealsstruct;
import pl.allegro.webapi.PostBuyFormItemDealsStruct;
import pl.allegro.webapi.PostBuyFormItemStruct;

import java.util.List;
import java.util.stream.Collectors;

public class ItemConv {

    private final static Logger logger = LoggerFactory.getLogger(ItemConv.class);

    public static Item convert(PostBuyFormItemStruct s, long transactionId, long sellerId) {
        float price = s.getPostBuyFormItPrice() > 0
                ? s.getPostBuyFormItPrice()
                : new BD(s.getPostBuyFormItAmount()).divide(s.getPostBuyFormItQuantity()).floatValue(2);

        logPriceDataForAllegroBehaviourAnalyze(s, transactionId);

        return new Item.Builder()
                .id(s.getPostBuyFormItId())
                .transactionId(transactionId)
                .sellerId(sellerId)
                .title(s.getPostBuyFormItTitle())
                .price(price)
                .quantity(s.getPostBuyFormItQuantity())
                .amount(s.getPostBuyFormItAmount())
                .build();
    }

    //-- helpers for understand how allegro is really working :)
    private static void logPriceDataForAllegroBehaviourAnalyze(PostBuyFormItemStruct s, long transactionId) {
        ArrayOfPostbuyformitemdealsstruct postBuyFormItDeals = s.getPostBuyFormItDeals();
        String log = String.format("\n** Transaction = %s. PostBuyFormItDeals is %s. ", transactionId, postBuyFormItDeals != null ? "Present" : "Null");
        log += String.format("\n**\tpostBuyFormItPrice = %s, getPostBuyFormItAmount = %s, getPostBuyFormItQuantity = %s", s.getPostBuyFormItPrice(), s.getPostBuyFormItAmount(), s.getPostBuyFormItQuantity());

        if (postBuyFormItDeals != null) {
            List<PostBuyFormItemDealsStruct> items = postBuyFormItDeals.getItem();
            String size = items != null ? Integer.toString(items.size()) : "null";
            log += String.format("\n**\tPostBuyFormItemDealsStruct.size = %s", size);
            log += logItemsDeals(items);
        }

        logger.debug(log);
    }

    private static String logItemsDeals(List<PostBuyFormItemDealsStruct> items) {
        return items == null
                ? ""
                : items
                    .stream()
                    .map(i -> String.format("\n**\t\tDealId = %s, dealFinalPrice = %s, dealQuantity = %s", i.getDealId(), i.getDealFinalPrice(), i.getDealQuantity()))
                    .collect(Collectors.joining(" "));
    }

}
