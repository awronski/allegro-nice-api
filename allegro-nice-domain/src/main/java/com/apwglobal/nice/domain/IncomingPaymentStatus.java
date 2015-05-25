package com.apwglobal.nice.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum IncomingPaymentStatus {

    STARTED("Rozpoczęta"),
    CANCELLED("Anulowana"),
    FINISHED("Zakończona");

    private String type;
    IncomingPaymentStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static final Map<String, IncomingPaymentStatus> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(IncomingPaymentStatus.values())
                        .collect(Collectors.toMap((IncomingPaymentStatus v) -> v.type, v -> v))
        );
    }
}
