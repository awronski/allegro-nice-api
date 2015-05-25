package com.apwglobal.nice.domain;

import com.apwglobal.bd.BD;
import com.apwglobal.nice.util.UnixDate;

import java.util.Date;
import java.util.Optional;

public class IncomingPayment {

    private long transactionId;
    private long sellerId;
    private long buyerId;
    private IncomingPaymentStatus status;
    private double amount;
    private Date receiveDate;
    private double price;
    private double postageAmount;
    private int itemsCounter;
    private boolean incomplete;
    private Optional<Long> mainTransactionId;

    private IncomingPayment(Builder builder) {
        transactionId = builder.transactionId;
        buyerId = builder.buyerId;
        sellerId = builder.sellerId;
        status = builder.status;
        amount = builder.amount;
        receiveDate = builder.receiveDate;
        price = builder.price;
        postageAmount = builder.postageAmount;
        itemsCounter = builder.itemsCounter;
        incomplete = builder.incomplete;
        mainTransactionId = builder.mainTransactionId;
    }

    public long getTransactionId() {
        return transactionId;
    }
    public long getBuyerId() {
        return buyerId;
    }
    public long getSellerId() {
        return sellerId;
    }
    public IncomingPaymentStatus getStatus() {
        return status;
    }
    public double getAmount() {
        return amount;
    }
    public Date getReceiveDate() {
        return receiveDate;
    }
    public double getPrice() {
        return price;
    }
    public double getPostageAmount() {
        return postageAmount;
    }
    public int getItemsCounter() {
        return itemsCounter;
    }
    public boolean isIncomplete() {
        return incomplete;
    }
    public Optional<Long> getMainTransactionId() {
        return mainTransactionId;
    }


    public static final class Builder {

        private long transactionId;
        private long buyerId;
        private long sellerId;
        private IncomingPaymentStatus status;
        private double amount;
        private Date receiveDate;
        private double price;
        private double postageAmount;
        private int itemsCounter;
        private boolean incomplete;
        private Optional<Long> mainTransactionId;

        public Builder() {
        }

        public Builder transactionId(long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder buyerId(long buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public Builder sellerId(long sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public Builder status(String status) {
            this.status = IncomingPaymentStatus.VALUES.get(status);
            return this;
        }

        public Builder amount(double amount) {
            this.amount = new BD(amount).doubleValue(2);
            return this;
        }

        public Builder receiveDate(long receiveDate) {
            this.receiveDate = UnixDate.toDate(receiveDate);
            return this;
        }

        public Builder price(double price) {
            this.price = new BD(price).doubleValue(2);
            return this;
        }

        public Builder postageAmount(double postageAmount) {
            this.postageAmount = new BD(postageAmount).doubleValue(2);
            return this;
        }

        public Builder itemsCounter(int itemsCounter) {
            this.itemsCounter = itemsCounter;
            return this;
        }

        public Builder incomplete(int incomplete) {
            this.incomplete = incomplete == 1;
            return this;
        }

        public Builder mainTransactionId(long mainTransactionId) {
            if (mainTransactionId == 0) {
                this.mainTransactionId = Optional.empty();
            } else {
                this.mainTransactionId = Optional.of(mainTransactionId);
            }
            return this;
        }

        public IncomingPayment build() {
            return new IncomingPayment(this);
        }
    }
}
