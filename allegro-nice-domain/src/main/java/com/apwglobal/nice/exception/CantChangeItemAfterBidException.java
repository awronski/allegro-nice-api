package com.apwglobal.nice.exception;

public class CantChangeItemAfterBidException extends AllegroException {

    public CantChangeItemAfterBidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantChangeItemAfterBidException(String message) {
        super(message);
    }

}
