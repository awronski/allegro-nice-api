package com.apwglobal.nice.journal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum JournalType {
    START("start"),
    END("end"),
    BID("bid"),
    NOW("now"),
    CHANGE("change"),
    CANCEL("cancel_bid");

    private String type;
    JournalType(String type) {
        this.type = type;
    }

    public static final Map<String, JournalType> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(JournalType.values())
                        .collect(Collectors.toMap((JournalType v) -> v.type, v -> v))
        );
    }
}
