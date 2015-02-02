package com.apwglobal.nice.service;

import com.apwglobal.nice.login.Credentials;
import pl.allegro.webapi.ServicePort;
import pl.allegro.webapi.ServiceService;

public class AllegroNiceApi {

    private ServicePort allegro;
    private Credentials cred;
    private AllegroSession session;
    private int countryId;


    private AllegroNiceApi(Builder builder) {
        cred = builder.credentials;
        countryId = builder.countryId;
        allegro = new ServiceService().getServicePort();
        login();
    }

    private void login() {

    }

    public AllegroSession getSession() {
        return session;
    }

    public static final class Builder {
        private Credentials credentials;
        private int countryId;

        public Builder() {
        }

        public Builder credentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public Builder countryId(int countryId) {
            this.countryId = countryId;
            return this;
        }

        public AllegroNiceApi build() {
            return new AllegroNiceApi(this);
        }
    }

}
