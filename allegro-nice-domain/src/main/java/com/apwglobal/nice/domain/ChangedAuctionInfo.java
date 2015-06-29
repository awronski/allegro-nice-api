package com.apwglobal.nice.domain;

import com.apwglobal.bd.BD;

public class ChangedAuctionInfo {

    private long itemId;
    private double surchargeAmount;
    private double totalAmount;

    public ChangedAuctionInfo() { }

    private ChangedAuctionInfo(Builder builder) {
        itemId = builder.itemId;
        totalAmount = builder.totalAmount;
        surchargeAmount = builder.surchargeAmount;
    }

    public long getItemId() {
        return itemId;
    }
    public double getSurchargeAmount() {
        return surchargeAmount;
    }
    public double getTotalAmount() {
        return totalAmount;
    }

    public static final class Builder {
        private long itemId;
        private double totalAmount;
        private double surchargeAmount;

        public Builder() {
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder totalAmount(float totalAmount) {
            this.totalAmount = new BD(totalAmount).doubleValue(2);;
            return this;
        }

        public Builder surchargeAmount(float surchargeAmount) {
            this.surchargeAmount = new BD(surchargeAmount).doubleValue(2);
            return this;
        }

        public ChangedAuctionInfo build() {
            return new ChangedAuctionInfo(this);
        }
    }

}
