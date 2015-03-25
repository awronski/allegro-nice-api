package com.apwglobal.nice.domain;

public class ChangedQty {

    private long itemId;
    private String info;
    private int left;
    private int sold;

    public ChangedQty() { }

    public long getItemId() {
        return itemId;
    }
    public String getInfo() {
        return info;
    }
    public int getLeft() {
        return left;
    }
    public int getSold() {
        return sold;
    }

    private ChangedQty(Builder builder) {
        itemId = builder.itemId;
        info = builder.info;
        left = builder.left;
        sold = builder.sold;
    }

    public static final class Builder {
        private long itemId;
        private String info;
        private int left;
        private int sold;

        public Builder() {
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder info(String info) {
            this.info = info;
            return this;
        }

        public Builder left(int left) {
            this.left = left;
            return this;
        }

        public Builder sold(int sold) {
            this.sold = sold;
            return this;
        }

        public ChangedQty build() {
            return new ChangedQty(this);
        }
    }

    @Override
    public String toString() {
        return "ChangedQty{" +
                "itemId=" + itemId +
                ", info='" + info + '\'' +
                ", left=" + left +
                ", sold=" + sold +
                '}';
    }

}
