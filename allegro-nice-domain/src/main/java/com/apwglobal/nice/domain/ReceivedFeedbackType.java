package com.apwglobal.nice.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum ReceivedFeedbackType {

    POS("POS"),
    NEG("NEG"),
    NEU("NEU"),
    MISSING("NULL");

    private String type;
    ReceivedFeedbackType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static final Map<String, ReceivedFeedbackType> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(ReceivedFeedbackType.values())
                        .collect(Collectors.toMap((ReceivedFeedbackType v) -> v.type, v -> v))
        );
    }
}
