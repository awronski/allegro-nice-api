package com.apwglobal.nice.deal;

import com.apwglobal.nice.util.UnixDate;

import java.time.LocalDateTime;

public class Deal {

    protected long dealEventId;
    protected DealType dealType;
    protected LocalDateTime dealEventTime;
    protected long dealId;
    protected long dealTransactionId;
    protected int dealSellerId;
    protected long dealItemId;
    protected int dealBuyerId;
    protected int dealQuantity;

    private Deal(Builder builder) {
        dealEventId = builder.dealEventId;
        dealType = builder.dealType;
        dealEventTime = builder.dealEventTime;
        dealId = builder.dealId;
        dealTransactionId = builder.dealTransactionId;
        dealSellerId = builder.dealSellerId;
        dealItemId = builder.dealItemId;
        dealBuyerId = builder.dealBuyerId;
        dealQuantity = builder.dealQuantity;
    }

    public long getDealEventId() {
        return dealEventId;
    }
    public DealType getDealType() {
        return dealType;
    }
    public LocalDateTime getDealEventTime() {
        return dealEventTime;
    }
    public long getDealId() {
        return dealId;
    }
    public long getDealTransactionId() {
        return dealTransactionId;
    }
    public int getDealSellerId() {
        return dealSellerId;
    }
    public long getDealItemId() {
        return dealItemId;
    }
    public int getDealBuyerId() {
        return dealBuyerId;
    }
    public int getDealQuantity() {
        return dealQuantity;
    }

    public static final class Builder {
        private long dealEventId;
        private DealType dealType;
        private LocalDateTime dealEventTime;
        private long dealId;
        private long dealTransactionId;
        private int dealSellerId;
        private long dealItemId;
        private int dealBuyerId;
        private int dealQuantity;

        public Builder() {
        }

        public Builder dealEventId(long dealEventId) {
            this.dealEventId = dealEventId;
            return this;
        }

        public Builder dealType(int dealEventType) {
            this.dealType = DealType.VALUES.get(dealEventType);
            return this;
        }

        public Builder dealEventTime(long dealEventTime) {
            this.dealEventTime = UnixDate.toDate(dealEventTime);
            return this;
        }

        public Builder dealId(long dealId) {
            this.dealId = dealId;
            return this;
        }

        public Builder dealTransactionId(long dealTransactionId) {
            this.dealTransactionId = dealTransactionId;
            return this;
        }

        public Builder dealSellerId(int dealSellerId) {
            this.dealSellerId = dealSellerId;
            return this;
        }

        public Builder dealItemId(long dealItemId) {
            this.dealItemId = dealItemId;
            return this;
        }

        public Builder dealBuyerId(int dealBuyerId) {
            this.dealBuyerId = dealBuyerId;
            return this;
        }

        public Builder dealQuantity(int dealQuantity) {
            this.dealQuantity = dealQuantity;
            return this;
        }

        public Deal build() {
            return new Deal(this);
        }
    }
}
