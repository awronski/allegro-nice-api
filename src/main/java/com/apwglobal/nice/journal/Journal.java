package com.apwglobal.nice.journal;

import com.apwglobal.nice.util.UnixDate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Journal {

    private Journal(Builder builder) {
        rowId = builder.rowId;
        itemId = builder.itemId;
        changeType = builder.changeType;
        changeDate = builder.changeDate;
        currentPrice = builder.currentPrice;
    }

    public enum CHANGE_TYPE {
        START("start"),
        END("end"),
        BID("bid"),
        NOW("now"),
        CHANGE("change"),
        CANCEL("cancel_bid");

        private String type;
        CHANGE_TYPE(String type) {
            this.type = type;
        }

        private static final Map<String, CHANGE_TYPE> VALUES;
        static {
            VALUES = new HashMap<>();
            Arrays.stream(CHANGE_TYPE.values())
                    .forEach(v -> VALUES.put(v.type, v));
        }
    }

    private long rowId;
    private long itemId;
    private CHANGE_TYPE changeType;
    private LocalDateTime changeDate;
    private double currentPrice;

    public long getRowId() {
        return rowId;
    }
    public long getItemId() {
        return itemId;
    }
    public CHANGE_TYPE getChangeType() {
        return changeType;
    }
    public LocalDateTime getChangeDate() {
        return changeDate;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }

    public static final class Builder {
        private long rowId;
        private long itemId;
        private CHANGE_TYPE changeType;
        private LocalDateTime changeDate;
        private double currentPrice;

        public Builder() {
        }

        public Builder rowId(long rowId) {
            this.rowId = rowId;
            return this;
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder changeType(String changeType) {
            this.changeType = CHANGE_TYPE.VALUES.get(changeType);
            return this;
        }

        public Builder changeDate(long unitTimestamp) {
            this.changeDate = UnixDate.toDate(unitTimestamp);
            return this;
        }

        public Builder currentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }

        public Journal build() {
            return new Journal(this);
        }
    }

    @Override
    public String toString() {
        return "Journal{" +
                "rowId=" + rowId +
                ", itemId=" + itemId +
                ", changeType=" + changeType +
                ", changeDate=" + changeDate +
                ", currentPrice=" + currentPrice +
                '}';
    }

}
