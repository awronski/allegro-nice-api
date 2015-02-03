package com.apwglobal.nice.util;

import com.apwglobal.nice.login.AbstractServiceBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class VersionUtilTest extends AbstractServiceBaseTest {

    @Test
    public void shouldReturnAllegroApiVersion() {
        Assert.assertNotNull(VersionUtil.getVersion(allegro, conf.getCountryId(), cred.getKey()));
    }

}