package com.apwglobal.nice.login;

import com.apwglobal.nice.service.AllegroNiceApi;
import org.junit.BeforeClass;

import java.io.IOException;

public class AbstractLoggedServiceBaseTest extends AbstractServiceBaseTest {

    protected static AllegroNiceApi api;

    @BeforeClass
    public static void abstractLoggedServiceSetup() throws IOException {
        api = new AllegroNiceApi.Builder()
                .conf(conf)
                .cred(cred)
                .build();
    }

}
