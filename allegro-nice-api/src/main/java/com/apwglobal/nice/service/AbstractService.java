package com.apwglobal.nice.service;

import com.apwglobal.nice.login.Credentials;
import pl.allegro.webapi.ServicePort;

public class AbstractService {

    protected ServicePort allegro;
    protected Credentials cred;
    protected Configuration conf;

    public AbstractService() {
    }

    public AbstractService(ServicePort allegro, Credentials cred, Configuration conf) {
        this.allegro = allegro;
        this.cred = cred;
        this.conf = conf;
    }

}
