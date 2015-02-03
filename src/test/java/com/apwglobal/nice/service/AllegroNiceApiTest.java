package com.apwglobal.nice.service;

import com.apwglobal.nice.login.AbstractLoggedServiceBaseTest;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AllegroNiceApiTest extends AbstractLoggedServiceBaseTest {

    @Test
    public void shouldNotReturnSessionId() {
        Assert.assertNull(api.getSession());
    }

    @Test
    public void shouldReturnSessionId() {
        assertNotNull(api.login().getSession());
    }

    @Test
    public void shouldReturnStatus() {
        assertNotNull(api.getStatus());
    }


}
