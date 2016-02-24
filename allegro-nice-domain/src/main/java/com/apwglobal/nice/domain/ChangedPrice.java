package com.apwglobal.nice.domain;

public class ChangedPrice {

    private long itemId;
    private String info;

    public ChangedPrice() { }

    public long getItemId() {
        return itemId;
    }
    public String getInfo() {
        return info;
    }

    private ChangedPrice(Builder builder) {
        itemId = builder.itemId;
        info = builder.info;
    }

    public static final class Builder {
        private long itemId;
        private String info;

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

        public ChangedPrice build() {
            return new ChangedPrice(this);
        }
    }

    @Override
    public String toString() {
        return "ChangedQty{" +
                "itemId=" + itemId +
                ", info='" + info + '\'' +
                '}';
    }

}
