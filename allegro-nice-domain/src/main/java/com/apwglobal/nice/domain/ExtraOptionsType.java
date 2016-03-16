package com.apwglobal.nice.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public enum ExtraOptionsType {

    BOLD(1),
    THUMB(2),
    BACKLIGHT(4),
    DISTINCTION(8),
    CATEGORY_PAGE(32),
    WATERMARK(64);

    private Integer type;
    ExtraOptionsType(Integer type) {
        this.type = type;
    }

    public static final Map<Integer, ExtraOptionsType> VALUES;
    static {
        VALUES = Collections.unmodifiableMap(
                Arrays.stream(ExtraOptionsType.values())
                        .collect(toMap((ExtraOptionsType v) -> v.type, v -> v))
        );
    }

    public static List<ExtraOptionsType> getOptions(int value) {
        return  VALUES
                .entrySet()
                .stream()
                .filter(e -> (e.getKey() & value) == e.getKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}

