package com.apwglobal.nice.exception;

public class RestApiException extends AllegroException {

    public RestApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestApiException(String message) {
        super(message);
    }

}
