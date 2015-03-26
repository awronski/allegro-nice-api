package com.apwglobal.nice.domain;

public class AuctionStatus {

    private long itemId;
    private String ref;
    private AuctionStatusType status;

    public AuctionStatus() { }

    public long getItemId() {
        return itemId;
    }
    public String getRef() {
        return ref;
    }
    public AuctionStatusType getStatus() {
        return status;
    }

    private AuctionStatus(Builder builder) {
        itemId = builder.itemId;
        ref = builder.ref;
        status = builder.status;
    }

    public static final class Builder {
        private long itemId;
        private String ref;
        private AuctionStatusType status;

        public Builder() {
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder ref(String ref) {
            this.ref = ref;
            return this;
        }

        public Builder status(AuctionStatusType status) {
            this.status = status;
            return this;
        }

        public AuctionStatus build() {
            return new AuctionStatus(this);
        }
    }

    @Override
    public String toString() {
        return "AuctionStatus{" +
                "itemId=" + itemId +
                ", ref='" + ref + '\'' +
                ", status=" + status +
                '}';
    }
}
