package com.apwglobal.nice.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum FieldId {

    TITLE(1),
    CATEGORY(2),
    DURATION(4),
    SELL_TYPE(29),
    QTY(5),
    PRICE(8),
    COUNTRY(9),
    STATE(10),
    CITY(11),
    TRANSPORT_PAID_BY(12),
    INVOICE(14),
    EXTRA_OPTIONS(15),
    IMAGE(16),
    DESC(24),
    UNIT(28),
    ZIP(32),
    PRICE_FOR_LETTER(43),
    PRICE_FOR_COURIER(44),
    PRICE_FOR_LETTER_NEXT_UNIT(143),
    PRICE_FOR_COURIER_NEXT_UNIT(144),
    MAX_QTY_IN_LETTER(243),
    MAX_QTY_IN_COURIER(244),
    SENDING_TIME(340),
    COLOR(3110);

    private int id;

    FieldId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static final Map<Integer, FieldId> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(FieldId.values())
                        .collect(Collectors.toMap((FieldId v) -> v.id, v -> v))
        );
    }

}
