package com.apwglobal.nice.system;

import com.apwglobal.nice.login.AbstractServiceBaseTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SystemSeviceTest extends AbstractServiceBaseTest {

    private static SystemService systemService;

    @BeforeClass
    public static void setup() {
        systemService = new SystemService(allegro, cred, conf);
    }

    @Test
    public void allSysStatus() {
        Assert.assertNotNull(systemService.getStatus());
    }

}
