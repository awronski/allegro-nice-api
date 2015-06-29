package com.apwglobal.nice.domain;

public class AuctionField {

    private int id;
    private FieldType.Type type;
    private Object value;

    public AuctionField() { }

    public AuctionField(int id, FieldType.Type type, Object value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }
    public FieldType.Type getType() {
        return type;
    }
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AuctionField{" +
                "id=" + id +
                ", type=" + type +
                ", value=" + value +
                '}';
    }

}
