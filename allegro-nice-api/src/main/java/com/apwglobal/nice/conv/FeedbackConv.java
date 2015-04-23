package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Feedback;
import pl.allegro.webapi.WaitFeedbackStruct;

public class FeedbackConv {

    public static Feedback convert(WaitFeedbackStruct f) {
        return new Feedback.Builder()
                .itemId(f.getFeItemId())
                .userId(f.getFeToUserId())
                .possibilityToAdd(f.getFePossibilityToAdd())
                .feedbackFor(f.getFeOp())
                .receivedFeedbackType(f.getFeAnsCommentType())
                .build();
    }
}
