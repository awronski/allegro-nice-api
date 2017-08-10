package com.apwglobal.nice.rest;

import com.google.gson.annotations.SerializedName;

public class RestApiErrorResult {

    @SerializedName("error")
    private String error;

    @SerializedName("error_description")
    private String errorDescription;

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    @Override
    public String toString() {
        return "RestApiErrorResult{" +
                "error='" + error + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }

}
