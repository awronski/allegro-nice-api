package com.apwglobal.nice.system;

import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.DoQueryAllSysStatusRequest;
import pl.allegro.webapi.ServicePort;
import pl.allegro.webapi.SysStatusType;

public class SystemService extends AbstractService {


    public SystemService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,62#method-output
     */
    public SysStatusType getStatus() {
        DoQueryAllSysStatusRequest request = new DoQueryAllSysStatusRequest(conf.getCountryId(), cred.getKey());
        return allegro.doQueryAllSysStatus(request)
                .getSysCountryStatus()
                .getItem()
                .stream()
                .filter(type -> type.getCountryId() == conf.getCountryId())
                .findAny()
                .get();
    }

}
