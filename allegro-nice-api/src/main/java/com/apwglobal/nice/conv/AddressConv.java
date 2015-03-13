package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Address;
import pl.allegro.webapi.PostBuyFormAddressStruct;

public class AddressConv {

    public static Address convert(PostBuyFormAddressStruct data) {
        return new Address.Builder()
                .fullname(data.getPostBuyFormAdrFullName())
                .company(data.getPostBuyFormAdrCompany())
                .street(data.getPostBuyFormAdrStreet())
                .code(data.getPostBuyFormAdrPostcode())
                .city(data.getPostBuyFormAdrCity())
                .countryId(data.getPostBuyFormAdrCountry())
                .phone(data.getPostBuyFormAdrPhone())
                .nip(data.getPostBuyFormAdrNip())
                .build();
    }

}
