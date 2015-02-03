package com.apwglobal.nice.service;

import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.login.LoginService;
import com.apwglobal.nice.system.SystemService;
import pl.allegro.webapi.ServiceService;

public class AllegroNiceApi extends AbstractService implements IAllegroNiceApi {

    private AllegroSession session;
    private LoginService loginService;
    private SystemService systemService;

    private AllegroNiceApi(Builder builder) {
        allegro = new ServiceService().getServicePort();
        cred = builder.cred;
        conf = builder.conf;
        loginService = new LoginService(allegro, cred, conf);
        systemService = new SystemService(allegro, cred, conf);
    }

    public IAllegroNiceApi login() {
        this.session = loginService.login();
        return this;
    }

    public AllegroSession getSession() {
        return session;
    }


    public static final class Builder {
        private Credentials cred;
        private Configuration conf;

        public Builder() {
        }

        public Builder cred(Credentials cred) {
            this.cred = cred;
            return this;
        }

        public Builder conf(Configuration conf) {
            this.conf = conf;
            return this;
        }

        public AllegroNiceApi build() {
            return new AllegroNiceApi(this);
        }
    }
}
