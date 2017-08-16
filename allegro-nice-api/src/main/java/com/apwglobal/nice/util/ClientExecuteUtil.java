package com.apwglobal.nice.util;

import com.apwglobal.nice.exception.RestApiException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientExecuteUtil {

    private final static Logger logger = LoggerFactory.getLogger(ClientExecuteUtil.class);

    @NotNull
    public static String execute(@NotNull HttpRequestBase httpRequest) {
        CloseableHttpClient client = HttpClients.createDefault();

        try (CloseableHttpResponse res = client.execute(httpRequest)) {
            HttpEntity entity = res.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RestApiException(e.getMessage(), e);
        }
    }

}
