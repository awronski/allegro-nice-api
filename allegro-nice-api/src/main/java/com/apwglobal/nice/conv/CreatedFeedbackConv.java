package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.CreatedFeedback;
import pl.allegro.webapi.FeedbackResultStruct;

public class CreatedFeedbackConv {

    public static CreatedFeedback convert(FeedbackResultStruct f) {
        return new CreatedFeedback.Builder()
                .id(f.getFeId())
                .itemId(f.getFeItemId())
                .faultCode(f.getFeFaultCode())
                .faultDesc(f.getFeFaultDesc())
                .build();
    }

}
