package com.apwglobal.nice.domain;

public class AuctionField<T> {

    private int id;
    private FieldType.Type type;
    private T value;

    public AuctionField() { }

    public AuctionField(int id, FieldType.Type type, T value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public AuctionField(FieldId id, FieldType.Type type, T value) {
        this.id = id.getId();
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }
    public FieldType.Type getType() {
        return type;
    }
    public T getValue() {
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
