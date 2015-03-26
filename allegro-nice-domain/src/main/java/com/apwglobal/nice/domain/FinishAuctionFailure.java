package com.apwglobal.nice.domain;

public class FinishAuctionFailure {

    private long itemId;
    private String errorCode;
    private String errorMessage;

    public FinishAuctionFailure() { }

    private FinishAuctionFailure(Builder builder) {
        itemId = builder.itemId;
        errorCode = builder.errorCode;
        errorMessage = builder.errorMessage;
    }

    public long getItemId() {
        return itemId;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public static final class Builder {

        private long itemId;
        private String errorCode;
        private String errorMessage;

        public Builder() {
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public FinishAuctionFailure build() {
            return new FinishAuctionFailure(this);
        }
    }

    @Override
    public String toString() {
        return "FinishAuctionFailure{" +
                "itemId=" + itemId +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
