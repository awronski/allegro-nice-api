package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.PostBuyForm;
import pl.allegro.webapi.PostBuyFormAddressStruct;
import pl.allegro.webapi.PostBuyFormDataStruct;

public class PostBuyFormConv {

    public static PostBuyForm convert(PostBuyFormDataStruct f) {

        PostBuyFormAddressStruct shipment = f.getPostBuyFormShipmentAddress();
        PostBuyFormAddressStruct invoice = f.getPostBuyFormInvoiceData();

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
                .invoice(AddressConv.convert(invoice))
                .shipment(AddressConv.convert(shipment))
                .build();
    }
}
