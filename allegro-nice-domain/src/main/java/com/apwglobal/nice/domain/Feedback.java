package com.apwglobal.nice.domain;

public class Feedback {

    private long itemId;
    private int userId;
    private FeedbackFor feedbackFor;
    private ReceivedFeedbackType receivedFeedbackType;
    private boolean possibilityToAdd;

    public Feedback() { }

    private Feedback(Builder builder) {
        itemId = builder.itemId;
        userId = builder.userId;
        feedbackFor = builder.feedbackFor;
        receivedFeedbackType = builder.receivedFeedbackType;
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
    public ReceivedFeedbackType getReceivedFeedbackType() {
        return receivedFeedbackType;
    }
    public boolean isPossibilityToAdd() {
        return possibilityToAdd;
    }

    public static final class Builder {
        private long itemId;
        private int userId;
        private FeedbackFor feedbackFor;
        private ReceivedFeedbackType receivedFeedbackType;
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
            this.receivedFeedbackType = ReceivedFeedbackType.VALUES.get(receivedFeedbackType);
            return this;
        }

        public Builder possibilityToAdd(int possibilityToAdd) {
            this.possibilityToAdd = possibilityToAdd == 1;
            return this;
        }

        public Feedback build() {
            return new Feedback(this);
        }
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "itemId=" + itemId +
                ", userId=" + userId +
                ", feedbackFor=" + feedbackFor +
                ", receivedFeedbackType=" + receivedFeedbackType +
                ", possibilityToAdd=" + possibilityToAdd +
                '}';
    }

}
