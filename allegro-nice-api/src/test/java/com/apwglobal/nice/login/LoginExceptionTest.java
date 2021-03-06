package com.apwglobal.nice.login;

import com.apwglobal.nice.exception.WebApiKeyException;
import com.apwglobal.nice.service.AllegroNiceApi;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.junit.Test;

public class LoginExceptionTest extends AbstractServiceBaseTest {

    @Test(expected = WebApiKeyException.class)
    public void shouldThrowWebApiKeyException() {
        Credentials badCred = new Credentials(0, "some_user", cred.getPassoword(), "incorrect");

        IAllegroNiceApi api = new AllegroNiceApi.Builder()
                .conf(conf)
                .cred(badCred)
                .test(test)
                .build();

        api.login();
    }

}
