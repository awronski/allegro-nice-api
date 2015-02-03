package com.apwglobal.nice.login;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LoginServiceTest extends AbstractLoggedServiceBaseTest {

    private static LoginService loginService;

    @BeforeClass
    public static void setup() {
        loginService = new LoginService(allegro, cred, conf);
    }

    @Test
    public void login() {
        assertNotNull(loginService.login().getSessionId());
    }

}
