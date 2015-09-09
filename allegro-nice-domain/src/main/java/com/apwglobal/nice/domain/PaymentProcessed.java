package com.apwglobal.nice.domain;

import java.util.Date;

public class PaymentProcessed {

    private long sellerId;
    private long transactionId;
    private Date date;
    private String ref;

    public PaymentProcessed() { }

    private PaymentProcessed(Builder builder) {
        sellerId = builder.sellerId;
        transactionId = builder.transactionId;
        date = builder.date;
        ref = builder.ref;
    }

    public long getTransactionId() {
        return transactionId;
    }
    public long getSellerId() {
        return sellerId;
    }
    public Date getDate() {
        return date;
    }
    public String getRef() {
        return ref;
    }

    public static final class Builder {
        private long sellerId;
        private long transactionId;
        private boolean processed;
        private Date date;
        private String ref;

        public Builder() {
        }

        public Builder sellerId(long sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public Builder transactionId(long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder processed(boolean processed) {
            this.processed = processed;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder ref(String ref) {
            this.ref = ref;
            return this;
        }

        public PaymentProcessed build() {
            return new PaymentProcessed(this);
        }
    }

    @Override
    public String toString() {
        return "PaymentProcessed{" +
                "sellerId=" + sellerId +
                "transactionId=" + transactionId +
                ", date=" + date +
                ", ref='" + ref + '\'' +
                '}';
    }

}
