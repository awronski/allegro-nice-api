package com.apwglobal.nice.exception;

import javax.xml.ws.soap.SOAPFaultException;
import java.util.function.Supplier;

public class AllegroExecutor {

    private static final AllegroExceptionConventer aec = new AllegroExceptionConventer();

    public static <T> T execute(Supplier<T> task) {
        try {
            return task.get();
        } catch (SOAPFaultException e) {
            throw aec.convertException(e.getFault().getFaultCode(), e);
        }
    }
}
