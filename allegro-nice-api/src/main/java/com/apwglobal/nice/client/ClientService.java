package com.apwglobal.nice.client;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;

import java.util.Arrays;
import java.util.List;

public class ClientService extends AbstractService {

    public ClientService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public List<ItemPostBuyDataStruct> getClientsDate(String session, long itemId) {
        DoGetPostBuyDataRequest request = new DoGetPostBuyDataRequest(session, new ArrayOfLong(Arrays.asList(itemId)));
        DoGetPostBuyDataResponse response = AllegroExecutor.execute(() -> allegro.doGetPostBuyData(request));
        return response.getItemsPostBuyData().getItem();
    }

}
