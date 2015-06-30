package com.apwglobal.nice.exception;

public class UnknownAllegroException extends AllegroException {

    public UnknownAllegroException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownAllegroException(String message) {
        super(message);
    }

}
