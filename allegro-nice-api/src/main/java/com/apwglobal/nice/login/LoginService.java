package com.apwglobal.nice.login;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.AllegroSession;
import com.apwglobal.nice.service.Configuration;
import com.apwglobal.nice.util.VersionUtil;
import pl.allegro.webapi.DoLoginEncRequest;
import pl.allegro.webapi.DoLoginEncResponse;
import pl.allegro.webapi.ServicePort;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class LoginService extends AbstractService {

    private static final String SHA_256 = "SHA-256";

    public LoginService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public AllegroSession login() {
        long version = AllegroExecutor.execute(() -> VersionUtil.getVersion(allegro, conf.getCountryId(), cred.getKey()));

        DoLoginEncRequest request = new DoLoginEncRequest(cred.getUsername(), encPassword(cred.getPassoword()), conf.getCountryId(), cred.getKey(), version);
        DoLoginEncResponse response = AllegroExecutor.execute(() -> allegro.doLoginEnc(request));

        return new AllegroSession.Builder()
                .sessionId(response.getSessionHandlePart())
                .userId(response.getUserId())
                .lastLoginDate(new Date())
                .build();
    }

    public String encPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            md.update(password.getBytes());
            return Base64.getEncoder().encodeToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
