package com.apwglobal.nice.system;

import com.apwglobal.nice.login.AbstractServiceTest;
import org.junit.Assert;
import org.junit.Test;

public class SystemSeviceTest extends AbstractServiceTest {

    @Test
    public void allSysStatus() {
        Assert.assertNotNull(SystemService.getStatus(allegro, countryId, cred.getKey()));
    }

}
