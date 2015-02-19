package com.apwglobal.nice.auction;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum ItemQuantityType {

    PCS(1),
    SETS(2),
    PAIRS(3);

    private int type;
    ItemQuantityType(int type) {
        this.type = type;
    }

    public static final Map<Integer, ItemQuantityType> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(ItemQuantityType.values())
                        .collect(Collectors.toMap((ItemQuantityType v) -> v.type, v -> v))
        );
    }
}
