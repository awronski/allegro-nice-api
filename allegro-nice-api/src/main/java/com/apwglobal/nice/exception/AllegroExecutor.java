package com.apwglobal.nice.exception;

import javax.xml.ws.soap.SOAPFaultException;
import java.util.function.Supplier;

public class AllegroExecutor {

    public static <T> T execute(Supplier<T> task) {
        try {
            return task.get();
        } catch (SOAPFaultException e) {
            switch (e.getFault().getFaultCode()) {

                case "ERR_USER_PASSWD":
                    throw new UserPasswordException(e.getMessage(), e);

                case "ERR_WEBAPI_KEY":
                    throw new WebApiKeyException(e.getMessage(), e);

                default:
                    throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }
}
