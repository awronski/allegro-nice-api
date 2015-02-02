package com.apwglobal.nice.system;

import pl.allegro.webapi.DoQueryAllSysStatusRequest;
import pl.allegro.webapi.ServicePort;
import pl.allegro.webapi.SysStatusType;

public class SystemService {

    public static SysStatusType getStatus(ServicePort allegro, int countryId, String key) {
        DoQueryAllSysStatusRequest request = new DoQueryAllSysStatusRequest(countryId, key);
        return allegro.doQueryAllSysStatus(request)
                .getSysCountryStatus()
                .getItem()
                .stream()
                .filter(type -> type.getCountryId() == countryId)
                .findAny()
                .get();
    }

}
