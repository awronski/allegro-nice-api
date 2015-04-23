package com.apwglobal.nice.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum FeedbackType {

    POS("POS"),
    NEG("NEG"),
    NEU("NEU"),
    MISSING("NULL");

    private String type;
    FeedbackType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static final Map<String, FeedbackType> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(FeedbackType.values())
                        .collect(Collectors.toMap((FeedbackType v) -> v.type, v -> v))
        );
    }
}
