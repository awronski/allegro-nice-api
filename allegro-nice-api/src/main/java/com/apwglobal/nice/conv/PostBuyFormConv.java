package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Address;
import com.apwglobal.nice.domain.Item;
import com.apwglobal.nice.domain.PostBuyForm;
import pl.allegro.webapi.PostBuyFormDataStruct;

import java.util.List;
import java.util.stream.Collectors;

public class PostBuyFormConv {

    public static PostBuyForm convert(PostBuyFormDataStruct f) {

        Address receiver = AddressConv.convert(f.getPostBuyFormShipmentAddress());
        Address orderer = AddressConv.convert(f.getPostBuyFormInvoiceData());

        List<Item> items = f.getPostBuyFormItems()
                .getItem()
                .stream()
                .map(i -> ItemConv.convert(i, f.getPostBuyFormId()))
                .collect(Collectors.toList());

        return new PostBuyForm.Builder()
                .transactionId(f.getPostBuyFormId())
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
                .receiver(receiver)
                .orderer(orderer)
                .items(items)
                .build();
    }
}
