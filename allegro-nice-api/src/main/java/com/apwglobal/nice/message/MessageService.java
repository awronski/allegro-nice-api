package com.apwglobal.nice.message;

import com.apwglobal.nice.conv.AllegroMessageConv;
import com.apwglobal.nice.domain.AllegroMessage;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.apwglobal.nice.util.UnixDate;
import pl.allegro.webapi.*;

import java.util.Date;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class MessageService extends AbstractService {


    public MessageService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,94#method-output
     */
    public List<ServiceInfoCatStruct> getServiceInfoCategories() {
        DoGetServiceInfoCategoriesRequest request = new DoGetServiceInfoCategoriesRequest(conf.getCountryId(), cred.getKey());
        return allegro.doGetServiceInfoCategories(request).getServiceInfoCatsList().getItem();
    }

    public List<AllegroMessage> getAllMessages(Date from) {
        long unixDate = UnixDate.toUnixTimestamp(from);
        return getServiceInfoCategories()
                .stream()
                .flatMap(cat -> getItem(unixDate, cat).stream())
                .map(AllegroMessageConv::convert)
                .sorted(comparing(AllegroMessage::getDate))
                .collect(toList());
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,93#method-output
     */
    private List<ServiceInfoStruct> getItem(long unixDate, ServiceInfoCatStruct cat) {
        return allegro.doGetServiceInfo(
                new DoGetServiceInfoRequest(conf.getCountryId(), cat.getAnCatId(), unixDate, null, cred.getKey())
        ).getServiceInfoItemsList().getItem();
    }

}
