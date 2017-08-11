package com.apwglobal.nice.login;

import com.apwglobal.nice.exception.RestApiException;
import com.apwglobal.nice.rest.RestApiErrorResult;
import com.apwglobal.nice.rest.RestApiSession;
import com.apwglobal.nice.util.ClientExecuteUtil;
import com.apwglobal.nice.util.RestCommandBuilder;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.jetbrains.annotations.NotNull;

import static java.util.Base64.getEncoder;

public class RestLoginService {

    private static final String PATH = "/auth/oauth/token";
    public static final String LOGIN_HOST = "ssl.allegro.pl";
    private final Credentials credentials;

    public RestLoginService(@NotNull Credentials cred) {
        this.credentials = cred;
    }

    public RestApiSession login(@NotNull String code) throws RestApiException {
        String response = ClientExecuteUtil.execute(createLognHttpPost(code));
        return getRestApiSession(response);
    }

    public RestApiSession refreshToken(@NotNull RestApiSession restApiSession) {
        String response = ClientExecuteUtil.execute(createRefreshTokenPost(restApiSession));
        return getRestApiSession(response);
    }

    private RestApiSession getRestApiSession(String response) {
        Gson gson = new Gson();
        if (response.contains("error")) {
            throw new RestApiException(gson.fromJson(response, RestApiErrorResult.class).toString());
        } else {
            return gson.fromJson(response, RestApiSession.class);
        }
    }

    @NotNull
    private HttpPost createLognHttpPost(@NotNull String code) {
        return new RestCommandBuilder()
                .host(LOGIN_HOST)
                .path(PATH)
                .addParam("grant_type", "authorization_code")
                .addParam("code", code)
                .addParam("api-key", credentials.getRestClientApiKey())
                .addParam("redirect_uri", credentials.getRestRedirectUri())
                .addHeader("Authorization", getAuth())
                .buildPost();
    }

    @NotNull
    private HttpPost createRefreshTokenPost(RestApiSession restApiSession) {
        return new RestCommandBuilder()
                .host(LOGIN_HOST)
                .path(PATH)
                .addParam("grant_type", "refresh_token")
                .addParam("refresh_token", restApiSession.getRefreshRoken())
                .addParam("api-redirect_uri", credentials.getRestRedirectUri())
                .addHeader("Authorization", getAuth())
                .addHeader("Api-Key", credentials.getRestClientApiKey())
                .buildPost();
    }

    private String getAuth() {
        String authValue = credentials.getRestClientId() + ":" + credentials.getRestClientSecret();
        return String.format("Basic %s", getEncoder().encodeToString(authValue.getBytes()));
    }

}
