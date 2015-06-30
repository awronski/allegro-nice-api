package com.apwglobal.nice.exception;

public class UserPasswordException extends AllegroException {

    public UserPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserPasswordException(String message) {
        super(message);
    }

}
