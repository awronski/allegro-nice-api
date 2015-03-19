package com.apwglobal.nice.sell;

import com.apwglobal.nice.conv.CategoryConv;
import com.apwglobal.nice.domain.Category;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.DoGetCatsDataRequest;
import pl.allegro.webapi.DoGetCatsDataResponse;
import pl.allegro.webapi.ServicePort;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class SellService extends AbstractService {


    public SellService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,46#method-output
     */
    public List<Category> getCategories() {
        DoGetCatsDataRequest req = new DoGetCatsDataRequest(conf.getCountryId(), 0l, cred.getKey());
        DoGetCatsDataResponse res = AllegroExecutor.execute(() -> allegro.doGetCatsData(req));
        return res.getCatsList().getItem()
                .stream()
                .map(CategoryConv::convert)
                .collect(toList());
    }

}
