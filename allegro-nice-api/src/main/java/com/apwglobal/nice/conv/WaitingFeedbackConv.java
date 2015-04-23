package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.WaitingFeedback;
import pl.allegro.webapi.WaitFeedbackStruct;

public class WaitingFeedbackConv {

    public static WaitingFeedback convert(WaitFeedbackStruct f) {
        return new WaitingFeedback.Builder()
                .itemId(f.getFeItemId())
                .userId(f.getFeToUserId())
                .possibilityToAdd(f.getFePossibilityToAdd())
                .feedbackFor(f.getFeOp())
                .receivedFeedbackType(f.getFeAnsCommentType())
                .build();
    }
}
