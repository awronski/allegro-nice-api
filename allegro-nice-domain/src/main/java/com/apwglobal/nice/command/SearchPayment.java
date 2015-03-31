package com.apwglobal.nice.command;

import java.util.Date;
import java.util.Optional;

public class SearchPayment {

    private Optional<Long> transactionId = Optional.empty();
    private Optional<Long> buyerId = Optional.empty();
    private Optional<String> email = Optional.empty();

    private Optional<Boolean> withInvoice = Optional.empty();
    private Optional<Boolean> withMsg = Optional.empty();

    private Optional<Boolean> processed = Optional.empty();

    private Optional<Date> from = Optional.empty();
    private Optional<Date> to = Optional.empty();

    private Optional<Integer> limit = Optional.empty();

    public SearchPayment() { }

    private SearchPayment(Builder builder) {
        transactionId = builder.transactionId;
        buyerId = builder.buyerId;
        email = builder.email;
        withInvoice = builder.withInvoice;
        limit = builder.limit;
        withMsg = builder.withMsg;
        from = builder.from;
        to = builder.to;
        processed = builder.processed;
    }

    public Optional<Long> getTransactionId() {
        return transactionId;
    }
    public Optional<Long> getBuyerId() {
        return buyerId;
    }
    public Optional<String> getEmail() {
        return email;
    }
    public Optional<Boolean> getWithInvoice() {
        return withInvoice;
    }
    public Optional<Boolean> getWithMsg() {
        return withMsg;
    }
    public Optional<Integer> getLimit() {
        return limit;
    }
    public Optional<Date> getFrom() {
        return from;
    }
    public Optional<Date> getTo() {
        return to;
    }
    public Optional<Boolean> getProcessed() {
        return processed;
    }

    public static final class Builder {
        private Optional<Long> transactionId;
        private Optional<Long> buyerId;
        private Optional<String> email;
        private Optional<Boolean> withInvoice;
        private Optional<Boolean> withMsg;
        private Optional<Boolean> processed;
        private Optional<Date> from;
        private Optional<Date> to;
        private Optional<Integer> limit;

        public Builder() {
        }

        public Builder transactionId(long transactionId) {
            this.transactionId = Optional.of(transactionId);
            return this;
        }

        public Builder buyerId(long buyerId) {
            this.buyerId = Optional.of(buyerId);
            return this;
        }

        public Builder email(String email) {
            this.email = Optional.of(email);
            return this;
        }

        public Builder withInvoice(boolean withInvoice) {
            this.withInvoice = Optional.of(withInvoice);
            return this;
        }

        public Builder withMsg(boolean withMsg) {
            this.withMsg = Optional.of(withMsg);
            return this;
        }

        public Builder limit(int limit) {
            this.limit = Optional.of(limit);
            return this;
        }

        public Builder from(Date from) {
            this.from = Optional.of(from);
            return this;
        }

        public Builder to(Date to) {
            this.to = Optional.of(to);
            return this;
        }

        public Builder processed(Boolean processed) {
            this.processed = Optional.of(processed);
            return this;
        }

        public SearchPayment build() {
            return new SearchPayment(this);
        }
    }

    @Override
    public String toString() {
        return "SearchPayment{" +
                "transactionId=" + transactionId +
                ", buyerId=" + buyerId +
                ", email=" + email +
                ", withInvoice=" + withInvoice +
                ", withMsg=" + withMsg +
                ", from=" + from +
                ", to=" + to +
                ", limit=" + limit +
                ", processed=" + processed +
                '}';
    }
}
