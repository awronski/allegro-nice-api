package com.apwglobal.nice.deal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum DealType {

    DEAL(1), TRANSACTION(2), CANCELLATION(3), PAYMENT(4);

    private int type;

    DealType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static final Map<Integer, DealType> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(DealType.values())
                        .collect(Collectors.toMap((DealType v) -> v.type, v -> v))
        );
    }

}
