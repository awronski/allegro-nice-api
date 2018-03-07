package com.apwglobal.nice.login;

import com.apwglobal.nice.service.AllegroNiceApi;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.junit.BeforeClass;

public class AbstractLoggedServiceBaseTest extends AbstractServiceBaseTest {

    protected static IAllegroNiceApi api;

    @BeforeClass
    public static void abstractLoggedServiceSetup() {
        api = new AllegroNiceApi.Builder()
                .conf(conf)
                .cred(cred)
                .test(test)
                .build();
    }

}
