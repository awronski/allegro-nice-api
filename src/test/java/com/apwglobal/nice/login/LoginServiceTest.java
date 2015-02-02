package com.apwglobal.nice.login;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LoginServiceTest extends AbstractLoggedServiceTest {

    @Test
    public void login() {
        assertNotNull(LoginService.login(allegro, cred, countryId));
    }

}
