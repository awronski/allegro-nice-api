package com.apwglobal.nice.util;

import pl.allegro.webapi.DoQueryAllSysStatusRequest;
import pl.allegro.webapi.ServicePort;

public class VersionUtil {

    public static long getVersion(ServicePort allegro, int countryId, String key) {
        DoQueryAllSysStatusRequest request = new DoQueryAllSysStatusRequest(countryId, key);
        return allegro.doQueryAllSysStatus(request)
                .getSysCountryStatus()
                .getItem()
                .stream()
                .filter(type -> type.getCountryId() == countryId)
                .findAny()
                .get()
                .getVerKey();
    }

}
