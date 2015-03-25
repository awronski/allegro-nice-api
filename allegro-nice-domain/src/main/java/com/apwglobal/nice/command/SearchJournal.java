package com.apwglobal.nice.command;

import com.apwglobal.nice.domain.JournalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Optional;

public class SearchJournal {

    private Optional<Integer> limit = Optional.empty();
    private Optional<Long> rowId = Optional.empty();
    private Optional<Long> itemId = Optional.empty();
    private Optional<JournalType> changeType = Optional.empty();

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Optional<Date> from = Optional.empty();

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Optional<Date> to = Optional.empty();

    public SearchJournal() { }

    private SearchJournal(Builder builder) {
        setLimit(builder.limit);
        setRowId(builder.rowId);
        setItemId(builder.itemId);
        setChangeType(builder.changeType);
        setFrom(builder.from);
        setTo(builder.to);
    }

    public Optional<Integer> getLimit() {
        return limit;
    }
    public void setLimit(Optional<Integer> limit) {
        this.limit = limit;
    }
    public Optional<Long> getRowId() {
        return rowId;
    }
    public void setRowId(Optional<Long> rowId) {
        this.rowId = rowId;
    }
    public Optional<Long> getItemId() {
        return itemId;
    }
    public void setItemId(Optional<Long> itemId) {
        this.itemId = itemId;
    }
    public Optional<JournalType> getChangeType() {
        return changeType;
    }
    public void setChangeType(Optional<JournalType> changeType) {
        this.changeType = changeType;
    }
    public Optional<Date> getFrom() {
        return from;
    }
    public void setFrom(Optional<Date> from) {
        this.from = from;
    }
    public Optional<Date> getTo() {
        return to;
    }
    public void setTo(Optional<Date> to) {
        this.to = to;
    }


    public static final class Builder {
        private Optional<Integer> limit;
        private Optional<Long> rowId;
        private Optional<Long> itemId;
        private Optional<JournalType> changeType;
        private Optional<Date> from;
        private Optional<Date> to;

        public Builder() {
        }

        public Builder limit(int limit) {
            this.limit = Optional.of(limit);
            return this;
        }

        public Builder rowId(long rowId) {
            this.rowId = Optional.of(rowId);
            return this;
        }

        public Builder itemId(long itemId) {
            this.itemId = Optional.of(itemId);
            return this;
        }

        public Builder changeType(JournalType changeType) {
            this.changeType = Optional.of(changeType);
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

        public SearchJournal build() {
            return new SearchJournal(this);
        }
    }
}
