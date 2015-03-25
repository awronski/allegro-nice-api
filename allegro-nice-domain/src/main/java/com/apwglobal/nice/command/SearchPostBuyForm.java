package com.apwglobal.nice.command;

import java.util.Optional;

public class SearchPostBuyForm {

    private Optional<Long> transactionId;
    private Optional<Long> buyerId;
    private Optional<String> email;

    private Optional<Boolean> withInvoice;
    private Optional<Boolean> withMsg;

    private Optional<Integer> limit;

    public SearchPostBuyForm() { }

    private SearchPostBuyForm(Builder builder) {
        transactionId = builder.transactionId;
        buyerId = builder.buyerId;
        email = builder.email;
        withInvoice = builder.withInvoice;
        limit = builder.limit;
        withMsg = builder.withMsg;
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

    public static final class Builder {
        private Optional<Long> transactionId;
        private Optional<Long> buyerId;
        private Optional<String> email;
        private Optional<Boolean> withInvoice;
        private Optional<Boolean> withMsg;
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

        public SearchPostBuyForm build() {
            return new SearchPostBuyForm(this);
        }
    }

    @Override
    public String toString() {
        return "SearchPostBuyForm{" +
                "transactionId=" + transactionId +
                ", buyerId=" + buyerId +
                ", email=" + email +
                ", withInvoice=" + withInvoice +
                ", withMsg=" + withMsg +
                ", limit=" + limit +
                '}';
    }

}
