package com.apwglobal.nice.domain;

public class CreateFeedback {

    private long itemId;
    private int toUserId;
    private String comment;
    private FeedbackType feedbackType;
    private FeedbackFor feedbackFor;

    public CreateFeedback() { }

    private CreateFeedback(Builder builder) {
        itemId = builder.itemId;
        toUserId = builder.toUserId;
        comment = builder.comment;
        feedbackType = builder.feedbackType;
        feedbackFor = builder.feedbackFor;
    }

    public long getItemId() {
        return itemId;
    }
    public int getToUserId() {
        return toUserId;
    }
    public String getComment() {
        return comment;
    }
    public FeedbackType getFeedbackType() {
        return feedbackType;
    }
    public FeedbackFor getFeedbackFor() {
        return feedbackFor;
    }

    public static final class Builder {
        private long itemId;
        private int toUserId;
        private String comment;
        private FeedbackType feedbackType;
        private FeedbackFor feedbackFor;

        public Builder() {
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder toUserId(int toUserId) {
            this.toUserId = toUserId;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder feedbackType(FeedbackType feedbackType) {
            this.feedbackType = feedbackType;
            return this;
        }

        public Builder feedbackFor(FeedbackFor feedbackFor) {
            this.feedbackFor = feedbackFor;
            return this;
        }

        public CreateFeedback build() {
            return new CreateFeedback(this);
        }
    }

    @Override
    public String toString() {
        return "CreateFeedback{" +
                "itemId=" + itemId +
                ", toUserId=" + toUserId +
                ", comment='" + comment + '\'' +
                ", feedbackType=" + feedbackType +
                ", feedbackFor=" + feedbackFor +
                '}';
    }
}
