package com.apwglobal.nice.deal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        VALUES = Collections.unmodifiableMap(new HashMap<>());
        Arrays.stream(DealType.values())
                .forEach(v -> VALUES.put(v.type, v));
    }

}
