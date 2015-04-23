package com.apwglobal.nice.domain;

import java.util.Optional;

public class CreatedFeedback {

    private Optional<Integer> id;
    private long itemId;
    private Optional<String> faultCode;
    private Optional<String> faultDesc;

    public CreatedFeedback() { }

    private CreatedFeedback(Builder builder) {
        id = builder.id;
        itemId = builder.itemId;
        faultCode = builder.faultCode;
        faultDesc = builder.faultDesc;
    }

    public Optional<Integer> getId() {
        return id;
    }
    public long getItemId() {
        return itemId;
    }
    public Optional<String> getFaultCode() {
        return faultCode;
    }
    public Optional<String> getFaultDesc() {
        return faultDesc;
    }

    public static final class Builder {
        private Optional<Integer> id;
        private long itemId;
        private Optional<String> faultCode;
        private Optional<String> faultDesc;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id == 0 ? Optional.empty() : Optional.of(id);
            return this;
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder faultCode(String faultCode) {
            this.faultCode = faultCode.isEmpty() ? Optional.empty() : Optional.of(faultCode);
            return this;
        }

        public Builder faultDesc(String faultDesc) {
            this.faultDesc = faultDesc.isEmpty() ? Optional.empty() : Optional.of(faultDesc);
            return this;
        }

        public CreatedFeedback build() {
            return new CreatedFeedback(this);
        }
    }

    @Override
    public String toString() {
        return "CreatedFeedback{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", faultCode=" + faultCode +
                ", faultDesc=" + faultDesc +
                '}';
    }

}
