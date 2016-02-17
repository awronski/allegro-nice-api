package com.apwglobal.nice.conv;

import com.apwglobal.bd.BD;
import com.apwglobal.nice.domain.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.webapi.ArrayOfPostbuyformitemdealsstruct;
import pl.allegro.webapi.PostBuyFormItemDealsStruct;
import pl.allegro.webapi.PostBuyFormItemStruct;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ItemConv {

    private final static Logger logger = LoggerFactory.getLogger(ItemConv.class);

    public static List<Item> convert(PostBuyFormItemStruct s, long transactionId, long sellerId) {
        logPriceDataForAllegroBehaviourAnalyze(s, transactionId);
        Optional<Float> defaultPrice = getDefaultPrice(s);

        return s.getPostBuyFormItDeals()
            .getItem()
            .stream()
            .map(d -> createItem(s, d, transactionId, sellerId, defaultPrice))
            .collect(toList());
    }

    private static Item createItem(PostBuyFormItemStruct s, PostBuyFormItemDealsStruct d, long transactionId, long sellerId, Optional<Float> defaultPrice) {
        float price = defaultPrice.orElse(d.getDealFinalPrice());
        return new Item.Builder()
                .formId(s.getPostBuyFormItId())
                .title(s.getPostBuyFormItTitle())
                .formDealId(d.getDealId())
                .transactionId(transactionId)
                .sellerId(sellerId)
                .price(price)
                .quantity(d.getDealQuantity())
                .amount(new BD(price).multiply(d.getDealQuantity()).floatValue(2))
                .build();
    }


    private static Optional<Float> getDefaultPrice(PostBuyFormItemStruct s) {
        return useDefaultPrice(s)
                ? Optional.of(new BD(s.getPostBuyFormItAmount()).divide(s.getPostBuyFormItQuantity()).floatValue(2))
                : Optional.empty();
    }

    private static boolean useDefaultPrice(PostBuyFormItemStruct s) {
        return s.getPostBuyFormItDeals() == null
                || s.getPostBuyFormItDeals().getItem().stream().anyMatch(d -> d.getDealFinalPrice() == 0);
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
