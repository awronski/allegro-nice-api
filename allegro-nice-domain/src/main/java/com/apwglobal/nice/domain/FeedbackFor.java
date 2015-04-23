package com.apwglobal.nice.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum FeedbackFor {

    SELLER(1),
    BUYER(2);

    private int type;
    FeedbackFor(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static final Map<Integer, FeedbackFor> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(FeedbackFor.values())
                        .collect(Collectors.toMap((FeedbackFor v) -> v.type, v -> v))
        );
    }

}
