package com.apwglobal.nice.login;

import org.junit.BeforeClass;
import pl.allegro.webapi.ServicePort;
import pl.allegro.webapi.ServiceService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractServiceTest {

    protected static ServicePort allegro = new ServiceService().getServicePort();
    protected static Credentials cred;
    protected static int countryId;

    @BeforeClass
    public static void abstractServiceSetup() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = AbstractServiceTest.class.getResourceAsStream("/test-credentions.properties");
        properties.load(resourceAsStream);

        cred = new Credentials(
                properties.getProperty("alegro.username"),
                properties.getProperty("alegro.password"),
                properties.getProperty("alegro.key")
        );
        countryId = Integer.valueOf(properties.getProperty("alegro.country"));
    }

}
