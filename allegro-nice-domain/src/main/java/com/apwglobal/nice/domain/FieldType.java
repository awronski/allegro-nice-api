package com.apwglobal.nice.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldType {

    private Type type;
    private Type valueType;
    private int defValue;
    private List<String> optsValues;
    private List<String> optValuesDesc;

    public FieldType() { }

    private FieldType(Builder builder) {
        type = builder.type;
        valueType = builder.valueType;
        defValue = builder.defValue;
        optsValues = builder.optsValues;
        optValuesDesc = builder.optValuesDesc;
    }

    public Type getType() {
        return type;
    }
    public Type getValueType() {
        return valueType;
    }
    public List<String> getOptsValues() {
        return optsValues;
    }
    public List<String> getOptValuesDesc() {
        return optValuesDesc;
    }
    public String getDefValue() {
        return optsValues.isEmpty() ? Integer.toString(defValue) : optsValues.get(defValue);
    }

    public enum Type {

        STRING(1),
        INTEGER(2),
        FLOAT(3),
        COMBOBOX(4),
        RADIOBUTTON(5),
        CHECKBOX(6),
        IMAGE(7),
        TEXT(8),
        UNIX_DATE(9),
        DATE(13);

        private int type;
        Type(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static final Map<Integer, Type> VALUES;
        static {
            VALUES = Collections.unmodifiableMap(
                    Arrays.stream(Type.values())
                            .collect(Collectors.toMap((Type v) -> v.type, v -> v))
            );
        }

    }

    public static final class Builder {
        private Type type;
        private Type valueType;
        private int defValue;
        private List<String> optsValues;
        private List<String> optValuesDesc;

        public Builder() {
        }

        public Builder type(int type) {
            this.type = Type.VALUES.get(type);
            return this;
        }

        public Builder valueType(int valueType) {
            this.valueType = Type.VALUES.get(valueType);
            return this;
        }

        public Builder defValue(int defValue) {
            this.defValue = defValue;
            return this;
        }

        public Builder optsValues(String optsValues) {
            if (optsValues.isEmpty()) {
                this.optsValues = Collections.emptyList();
            } else {
                this.optsValues = Arrays.asList(optsValues.split("\\|"));
            }
            return this;
        }

        public Builder optValuesDesc(String optValuesDesc) {
            if (optValuesDesc.isEmpty()) {
                this.optValuesDesc = Collections.emptyList();
            } else {
                this.optValuesDesc = Arrays.asList(optValuesDesc.split("\\|"));
            }
            return this;
        }

        public FieldType build() {
            return new FieldType(this);
        }
    }

    @Override
    public String toString() {
        return "FieldType{" +
                "type=" + type +
                ", valueType=" + valueType +
                ", defValue=" + defValue +
                ", optsValues='" + optsValues + '\'' +
                ", optValuesDesc='" + optValuesDesc + '\'' +
                '}';
    }
}
