package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.AllegroMessage;
import com.apwglobal.nice.util.UnixDate;
import pl.allegro.webapi.ServiceInfoStruct;

import java.util.Base64;

public class AllegroMessageConv {

    public static AllegroMessage convert(ServiceInfoStruct info) {
        return new AllegroMessage.Builder()
                .catId(info.getAnCatId())
                .title(info.getAnItTitle())
                .body(new String(Base64.getDecoder().decode(info.getAnItBody())))
                .date(UnixDate.toDate(info.getAnItDate()))
                .build();
    }
}
