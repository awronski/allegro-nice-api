package com.apwglobal.nice.exception;

public class WebApiKeyException extends AllegroException {

    public WebApiKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebApiKeyException(String message) {
        super(message);
    }

}
