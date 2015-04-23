package com.apwglobal.nice.feedback;

import com.apwglobal.nice.conv.CreatedFeedbackConv;
import com.apwglobal.nice.conv.WaitingFeedbackConv;
import com.apwglobal.nice.domain.CreateFeedback;
import com.apwglobal.nice.domain.CreatedFeedback;
import com.apwglobal.nice.domain.WaitingFeedback;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.google.common.collect.Lists;
import pl.allegro.webapi.*;
import rx.Observable;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FeedbackService extends AbstractService {

    public FeedbackService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,106#method-output
     */
    public int getWaitingFeedbackCounter(String session) {
        DoGetWaitingFeedbacksCountRequest req = new DoGetWaitingFeedbacksCountRequest(session);
        DoGetWaitingFeedbacksCountResponse res = AllegroExecutor.execute(() -> allegro.doGetWaitingFeedbacksCount(req));
        return res.getFeedbackCount();
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,105#method-output
     */
    public Observable<WaitingFeedback> getWaitingFeedbacks(String session) {
        return Observable.from(() -> new FeedbackIterator(session, 0));
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,43#method-output
     */
    public List<CreatedFeedback> createFeedbacks(String session, List<CreateFeedback> feedbacks) {
        return Lists.partition(feedbacks, 25)
                .stream()
                .flatMap(f -> createFeedback(session, f).stream())
                .collect(toList());
    }

    private List<CreatedFeedback> createFeedback(String session, List<CreateFeedback> cf) {
        ArrayOfFeedbackmanystruct feedbacks = new ArrayOfFeedbackmanystruct(
                cf.stream()
                        .map(this::convertFeedbackManyStruct)
                        .collect(toList())
        );
        DoFeedbackManyRequest req = new DoFeedbackManyRequest(session, feedbacks);
        DoFeedbackManyResponse res = AllegroExecutor.execute(() -> allegro.doFeedbackMany(req));
        return res.getFeResults().getItem()
                .stream()
                .map(CreatedFeedbackConv::convert)
                .collect(toList());
    }

    private FeedbackManyStruct convertFeedbackManyStruct(CreateFeedback f) {
        FeedbackManyStruct struct = new FeedbackManyStruct();
        struct.setFeComment(f.getComment());
        struct.setFeCommentType(f.getFeedbackType().getType());
        struct.setFeItemId(f.getItemId());
        struct.setFeToUserId(f.getToUserId());
        struct.setFeOp(f.getFeedbackFor().getType());
        return struct;
    }

    private class FeedbackIterator extends AbstractAllegroIterator<WaitingFeedback> {

        public FeedbackIterator(String session, long startingPoint) {
            super(session, startingPoint);
        }

        @Override
        protected List<WaitingFeedback> doFetch() {
            DoGetWaitingFeedbacksRequest req = new DoGetWaitingFeedbacksRequest(session, (int) startingPoint, 200);
            return AllegroExecutor.execute(() -> allegro.doGetWaitingFeedbacks(req)).getFeWaitList().getItem()
                    .stream()
                    .map(WaitingFeedbackConv::convert)
                    .collect(toList());
        }

        @Override
        protected long getItemId(WaitingFeedback journal) {
            this.startingPoint += 200;
            return startingPoint;
        }
    }

}
