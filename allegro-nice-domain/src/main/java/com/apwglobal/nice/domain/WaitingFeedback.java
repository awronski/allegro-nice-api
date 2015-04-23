package com.apwglobal.nice.domain;

public class WaitingFeedback {

    private long itemId;
    private int userId;
    private FeedbackFor feedbackFor;
    private FeedbackType feedbackType;
    private boolean possibilityToAdd;

    public WaitingFeedback() { }

    private WaitingFeedback(Builder builder) {
        itemId = builder.itemId;
        userId = builder.userId;
        feedbackFor = builder.feedbackFor;
        feedbackType = builder.feedbackType;
        possibilityToAdd = builder.possibilityToAdd;
    }

    public long getItemId() {
        return itemId;
    }
    public int getUserId() {
        return userId;
    }
    public FeedbackFor getFeedbackFor() {
        return feedbackFor;
    }
    public FeedbackType getFeedbackType() {
        return feedbackType;
    }
    public boolean isPossibilityToAdd() {
        return possibilityToAdd;
    }

    public static final class Builder {
        private long itemId;
        private int userId;
        private FeedbackFor feedbackFor;
        private FeedbackType feedbackType;
        private boolean possibilityToAdd;

        public Builder() {
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder feedbackFor(int feedbackFor) {
            this.feedbackFor = FeedbackFor.VALUES.get(feedbackFor);
            return this;
        }

        public Builder receivedFeedbackType(String receivedFeedbackType) {
            this.feedbackType = FeedbackType.VALUES.get(receivedFeedbackType);
            return this;
        }

        public Builder possibilityToAdd(int possibilityToAdd) {
            this.possibilityToAdd = possibilityToAdd == 1;
            return this;
        }

        public WaitingFeedback build() {
            return new WaitingFeedback(this);
        }
    }

    @Override
    public String toString() {
        return "WaitingFeedback{" +
                "itemId=" + itemId +
                ", userId=" + userId +
                ", feedbackFor=" + feedbackFor +
                ", feedbackType=" + feedbackType +
                ", possibilityToAdd=" + possibilityToAdd +
                '}';
    }

}
