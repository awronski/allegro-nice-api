package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Address;
import com.apwglobal.nice.domain.Item;
import com.apwglobal.nice.domain.Payment;
import pl.allegro.webapi.PostBuyFormAddressStruct;
import pl.allegro.webapi.PostBuyFormDataStruct;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class PaymentConv {

    public static Payment convert(PostBuyFormDataStruct f, Map<Integer, String> countries) {

        Address receiver = convertAddress(f.getPostBuyFormShipmentAddress(), countries);
        Address orderer = convertAddress(f.getPostBuyFormInvoiceData(), countries);

        List<Item> items = f.getPostBuyFormItems()
                .getItem()
                .stream()
                .map(i -> ItemConv.convert(i, f.getPostBuyFormId()))
                .collect(toList());

        return new Payment.Builder()
                .transactionId(f.getPostBuyFormId())
                .buyerId(f.getPostBuyFormBuyerId())
                .email(f.getPostBuyFormBuyerEmail())
                .date(f.getPostBuyFormDateInit())
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
