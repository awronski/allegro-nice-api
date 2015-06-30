package com.apwglobal.nice.exception;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AllegroExceptionConventer {

    private Map<String, Class<? extends AllegroException>> mapping;

    public AllegroExceptionConventer() {
        mapping = new ConcurrentHashMap<>();
        mapping.put("ERR_USER_PASSWD", UserPasswordException.class);
        mapping.put("ERR_WEBAPI_KEY", WebApiKeyException.class);
        mapping.put("ERR_CANT_CHANGE_.+_AFTER_BID", CantChangeItemAfterBidException.class);
    }

    public AllegroException convertException(String code, Exception e) {
        Class<? extends AllegroException> aClass = mapping
                .entrySet()
                .stream()
                .filter(es -> code.matches(es.getKey()))
                .findAny()
                .map(Map.Entry::getValue)
                .orElse(UnknownAllegroException.class);

        return createException(e, aClass);
    }

    private AllegroException createException(Exception soapEx, Class<? extends AllegroException> aClass) {
        AllegroException ex;
        try {
            Constructor<? extends AllegroException> constructor = aClass.getConstructor(String.class, Throwable.class);
            ex = constructor.newInstance(soapEx.getMessage(), soapEx);
        } catch (Exception e1) {
            ex = new UnknownAllegroException(e1.getMessage(), e1);
        }

        return ex;
    }

}
