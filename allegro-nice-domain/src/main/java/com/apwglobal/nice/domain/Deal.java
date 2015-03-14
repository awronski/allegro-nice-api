package com.apwglobal.nice.domain;

import com.apwglobal.nice.util.UnixDate;

import java.util.Date;
import java.util.Optional;

public class Deal {

    protected long eventId;
    protected DealType dealType;
    protected Date eventTime;
    protected long dealId;
    protected Optional<Long> transactionId;
    protected int sellerId;
    protected long itemId;
    protected int buyerId;
    protected int quantity;

    public Deal() { }

    private Deal(Builder builder) {
        eventId = builder.eventId;
        dealType = builder.dealType;
        eventTime = builder.eventTime;
        dealId = builder.dealId;
        transactionId = builder.transactionId;
        sellerId = builder.sellerId;
        itemId = builder.itemId;
        buyerId = builder.buyerId;
        quantity = builder.quantity;
    }


    public long getEventId() {
        return eventId;
    }
    public DealType getDealType() {
        return dealType;
    }
    public Date getEventTime() {
        return eventTime;
    }
    public long getDealId() {
        return dealId;
    }
    public Optional<Long> getTransactionId() {
        return transactionId;
    }
    public int getSellerId() {
        return sellerId;
    }
    public long getItemId() {
        return itemId;
    }
    public int getBuyerId() {
        return buyerId;
    }
    public int getQuantity() {
        return quantity;
    }

    public static final class Builder {
        private long eventId;
        private DealType dealType;
        private Date eventTime;
        private long dealId;
        private Optional<Long> transactionId;
        private int sellerId;
        private long itemId;
        private int buyerId;
        private int quantity;

        public Builder() {
        }

        public Builder eventId(long eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder dealType(int dealType) {
            this.dealType = DealType.VALUES.get(dealType);
            return this;
        }

        public Builder eventTime(long eventTime) {
            this.eventTime = UnixDate.toDate(eventTime);
            return this;
        }

        public Builder dealId(long dealId) {
            this.dealId = dealId;
            return this;
        }

        public Builder transactionId(long transactionId) {
            if (transactionId == 0) {
                this.transactionId = Optional.empty();
            } else {
                this.transactionId = Optional.of(transactionId);
            }
            return this;
        }

        public Builder sellerId(int sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder buyerId(int buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Deal build() {
            return new Deal(this);
        }
    }

    @Override
    public String toString() {
        return "Deal{" +
                "eventId=" + eventId +
                ", dealType=" + dealType +
                ", eventTime=" + eventTime +
                ", dealId=" + dealId +
                ", transactionId=" + transactionId +
                ", itemId=" + itemId +
                ", buyerId=" + buyerId +
                ", quantity=" + quantity +
                '}';
    }
}
