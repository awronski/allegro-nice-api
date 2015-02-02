package com.apwglobal.nice.login;

import com.apwglobal.nice.service.AllegroSession;
import com.apwglobal.nice.system.SystemService;
import pl.allegro.webapi.DoLoginRequest;
import pl.allegro.webapi.DoLoginResponse;
import pl.allegro.webapi.ServicePort;

public class LoginService {

    public static AllegroSession login(ServicePort allegro, Credentials cred, int countryId) {
        long version = SystemService.getStatus(allegro, countryId, cred.getKey()).getVerKey();

        DoLoginRequest request = new DoLoginRequest(cred.getUsername(), cred.getPassowrd(), countryId, cred.getKey(), version);
        DoLoginResponse response = allegro.doLogin(request);

        return new AllegroSession.Builder()
                .versionId(version)
                .sessionId(response.getSessionHandlePart())
                .userId(response.getUserId())
                .build();
    }
}
