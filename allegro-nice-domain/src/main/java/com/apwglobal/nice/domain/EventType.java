package com.apwglobal.nice.domain;

public enum EventType {
    USER(0), All(1);

    private int type;

    EventType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
