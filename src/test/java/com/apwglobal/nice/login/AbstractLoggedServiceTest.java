package com.apwglobal.nice.login;

import com.apwglobal.nice.service.AllegroNiceApi;
import org.junit.BeforeClass;

import java.io.IOException;

public class AbstractLoggedServiceTest extends AbstractServiceTest {

    static AllegroNiceApi api;

    @BeforeClass
    public static void abstractLoggedServiceSetup() throws IOException {
        api = new AllegroNiceApi.Builder()
                .countryId(countryId)
                .credentials(cred)
                .build();
    }

}
