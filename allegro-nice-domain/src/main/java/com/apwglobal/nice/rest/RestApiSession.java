package com.apwglobal.nice.rest;

import com.google.gson.annotations.SerializedName;

public class RestApiSession {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshRoken;


    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshRoken() {
        return refreshRoken;
    }

}
