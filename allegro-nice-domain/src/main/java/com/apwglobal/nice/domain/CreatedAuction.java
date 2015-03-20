package com.apwglobal.nice.domain;

public class CreatedAuction {

    private long itemId;
    private String info;
    private boolean allegroStandard;

    public CreatedAuction() { }

    private CreatedAuction(Builder builder) {
        itemId = builder.itemId;
        info = builder.info;
        allegroStandard = builder.allegroStandard;
    }

    public long getItemId() {
        return itemId;
    }
    public String getInfo() {
        return info;
    }
    public boolean isAllegroStandard() {
        return allegroStandard;
    }

    public static final class Builder {
        private long itemId;
        private String info;
        private boolean allegroStandard;

        public Builder() {
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder info(String price) {
            this.info = price;
            return this;
        }

        public Builder allegroStandard(int allegroStandard) {
            this.allegroStandard = allegroStandard == 1;
            return this;
        }

        public CreatedAuction build() {
            return new CreatedAuction(this);
        }
    }

    @Override
    public String toString() {
        return "CreatedAuction{" +
                "itemId=" + itemId +
                ", price='" + info + '\'' +
                ", allegroStandard=" + allegroStandard +
                '}';
    }
}
