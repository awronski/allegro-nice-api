package com.apwglobal.nice.login;

import com.apwglobal.nice.exception.RestApiException;
import com.apwglobal.nice.rest.RestApiErrorResult;
import com.apwglobal.nice.rest.RestApiSession;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

import static java.util.Base64.getEncoder;

public class RestLoginService {

    private final static Logger logger = LoggerFactory.getLogger(RestLoginService.class);
    private final Credentials credentials;

    public RestLoginService(@NotNull Credentials cred) {
        this.credentials = cred;
    }

    public RestApiSession login(@NotNull String code) throws RestApiException {
        return execute(createLognHttpPost(code));
    }

    public RestApiSession refreshToken(@NotNull RestApiSession restApiSession) {
        return execute(createRefreshTokenPost(restApiSession));
    }

    private RestApiSession execute(HttpPost post) {
        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse res = client.execute(post);) {
            HttpEntity entity = res.getEntity();
            String response = EntityUtils.toString(entity, "UTF-8");
            return getRestApiSession(response);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RestApiException(e.getMessage(), e);
        }
    }

    private RestApiSession getRestApiSession(String response) throws Exception {
        Gson gson = new Gson();
        if (response.contains("error")) {
            throw new Exception(gson.fromJson(response, RestApiErrorResult.class).toString());
        } else {
            return gson.fromJson(response, RestApiSession.class);
        }
    }

    @NotNull
    private HttpPost createLognHttpPost(@NotNull String code) {
        URIBuilder builder = new URIBuilder()
                .setScheme("https").setHost("ssl.allegro.pl").setPath("/auth/oauth/token")
                .setParameter("grant_type", "authorization_code")
                .setParameter("code", code)
                .setParameter("api-key", credentials.getRestClientApiKey())
                .setParameter("redirect_uri", credentials.getRestRedirectUri());

        HttpPost post = null;
        try {
            post = new HttpPost(builder.build());
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
        post.setHeader("Authorization", getAuth());
        return post;
    }

    @NotNull
    private HttpPost createRefreshTokenPost(RestApiSession restApiSession) {
        URIBuilder builder = new URIBuilder()
                .setScheme("https").setHost("ssl.allegro.pl").setPath("/auth/oauth/token")
                .setParameter("grant_type", "refresh_token")
                .setParameter("refresh_token", restApiSession.getRefreshRoken())
                .setParameter("redirect_uri", credentials.getRestRedirectUri());

        HttpPost post = null;
        try {
            post = new HttpPost(builder.build());
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
        post.setHeader("Authorization", getAuth());
        post.setHeader("Api-Key", credentials.getRestClientApiKey());
        return post;
    }

    private String getAuth() {
        String authValue = credentials.getRestClientId() + ":" + credentials.getRestClientSecret();
        return String.format("Basic %s", getEncoder().encodeToString(authValue.getBytes()));
    }

}
