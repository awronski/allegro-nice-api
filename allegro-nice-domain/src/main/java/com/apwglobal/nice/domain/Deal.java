package com.apwglobal.nice.domain;

import com.apwglobal.nice.util.UnixDate;

import java.util.Date;
import java.util.Optional;

public class Deal {

    protected long dealEventId;
    protected DealType dealType;
    protected Date dealEventTime;
    protected long dealId;
    protected long dealTransactionId;
    protected int dealSellerId;
    protected long dealItemId;
    protected int dealBuyerId;
    protected int dealQuantity;
    protected Optional<Object> postBuyFormDataStruct;

    public Deal() { }

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
    public Date getDealEventTime() {
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
    public Optional<Object> getPostBuyFormDataStruct() {
        return postBuyFormDataStruct;
    }

    public void setPostBuyFormDataStruct(Optional<Object> postBuyFormDataStruct) {
        this.postBuyFormDataStruct = postBuyFormDataStruct;
    }

    public static final class Builder {
        private long dealEventId;
        private DealType dealType;
        private Date dealEventTime;
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
