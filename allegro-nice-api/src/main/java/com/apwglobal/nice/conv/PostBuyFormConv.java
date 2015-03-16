package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Address;
import com.apwglobal.nice.domain.Item;
import com.apwglobal.nice.domain.PostBuyForm;
import pl.allegro.webapi.PostBuyFormAddressStruct;
import pl.allegro.webapi.PostBuyFormDataStruct;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class PostBuyFormConv {

    public static PostBuyForm convert(PostBuyFormDataStruct f, Map<Integer, String> countries) {

        Address receiver = convertAddress(f.getPostBuyFormShipmentAddress(), countries);
        Address orderer = convertAddress(f.getPostBuyFormInvoiceData(), countries);

        List<Item> items = f.getPostBuyFormItems()
                .getItem()
                .stream()
                .map(i -> ItemConv.convert(i, f.getPostBuyFormId()))
                .collect(toList());

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

    private static Address convertAddress(PostBuyFormAddressStruct address, Map<Integer, String> countries) {
        return AddressConv.convert(
                address,
                countries.get(address.getPostBuyFormAdrCountry())
        );
    }
}
