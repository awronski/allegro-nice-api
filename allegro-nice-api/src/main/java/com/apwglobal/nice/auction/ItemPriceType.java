package com.apwglobal.nice.auction;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum ItemPriceType {

    BUY_NOW(1),
    CURRENT(2),
    START(3),
    MIN(4);

    private int type;
    ItemPriceType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static final Map<Integer, ItemPriceType> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(ItemPriceType.values())
                        .collect(Collectors.toMap((ItemPriceType v) -> v.type, v -> v))
        );
    }

}
