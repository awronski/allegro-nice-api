package com.apwglobal.nice.exception;

public abstract class AllegroException extends RuntimeException {

    public AllegroException(String message, Throwable cause) {
        super(message, cause);
    }

    public AllegroException(String message) {
        super(message);
    }

}
