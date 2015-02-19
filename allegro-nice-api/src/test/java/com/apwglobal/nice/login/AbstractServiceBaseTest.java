package com.apwglobal.nice.login;

import com.apwglobal.nice.service.Configuration;
import org.junit.BeforeClass;
import pl.allegro.webapi.ServicePort;
import pl.allegro.webapi.ServiceService;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public abstract class AbstractServiceBaseTest {

    protected static ServicePort allegro;
    protected static Credentials cred;
    protected static Configuration conf;

    @BeforeClass
    public static void abstractServiceSetup() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = AbstractServiceBaseTest.class.getResourceAsStream("/test-credentions.properties");
        properties.load(resourceAsStream);

        cred = new Credentials(
                properties.getProperty("alegro.username"),
                properties.getProperty("alegro.password"),
                properties.getProperty("alegro.key")
        );

        allegroIncorrectCertificateWorkaround();
        createAllegroService(properties);

        int countryId = Integer.valueOf(properties.getProperty("alegro.country"));
        conf = new Configuration(countryId);
    }

    private static void createAllegroService(Properties properties) throws MalformedURLException {
        String url = properties.getProperty("allegro.wsdl");
        String serviceName = properties.getProperty("allegro.serviceName");
        QName SERVICE_PORT = new QName(serviceName, "servicePort");
        QName SERVICE_NAME = new QName(serviceName, "serviceService");
        allegro = new ServiceService(new URL(url), SERVICE_NAME).getPort(SERVICE_PORT, ServicePort.class);
    }

    private static void allegroIncorrectCertificateWorkaround() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
