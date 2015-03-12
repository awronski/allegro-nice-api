package com.apwglobal.nice.message;

import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.apwglobal.nice.util.UnixDate;
import pl.allegro.webapi.DoGetServiceInfoCategoriesRequest;
import pl.allegro.webapi.DoGetServiceInfoRequest;
import pl.allegro.webapi.ServiceInfoCatStruct;
import pl.allegro.webapi.ServicePort;

import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MessageService extends AbstractService {


    public MessageService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public List<ServiceInfoCatStruct> getServiceInfoCategories() {
        DoGetServiceInfoCategoriesRequest request = new DoGetServiceInfoCategoriesRequest(conf.getCountryId(), cred.getKey());
        return allegro.doGetServiceInfoCategories(request).getServiceInfoCatsList().getItem();
    }


    public List<AllegroMessage> getAllMessages(Date from) {
        long unixDate = UnixDate.toUnixTimestamp(from);
        return getServiceInfoCategories()
                .stream()
                .flatMap(
                        cat -> allegro.doGetServiceInfo(
                                new DoGetServiceInfoRequest(conf.getCountryId(), cat.getAnCatId(), unixDate, null, cred.getKey())
                        ).getServiceInfoItemsList().getItem().stream()
                )
                .map(
                        info -> new AllegroMessage.Builder()
                                .catId(info.getAnCatId())
                                .title(info.getAnItTitle())
                                .body(new String(Base64.getDecoder().decode(info.getAnItBody())))
                                .date(UnixDate.toDate(info.getAnItDate()))
                                .build()
                )
                .sorted(Comparator.comparing(AllegroMessage::getDate))
                .collect(Collectors.toList());
    }

}
