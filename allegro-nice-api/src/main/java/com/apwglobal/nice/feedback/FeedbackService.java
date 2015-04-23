package com.apwglobal.nice.feedback;

import com.apwglobal.nice.conv.WaitingFeedbackConv;
import com.apwglobal.nice.domain.CreateFeedback;
import com.apwglobal.nice.domain.CreatedFeedback;
import com.apwglobal.nice.domain.WaitingFeedback;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;
import rx.Observable;

import java.util.List;
import java.util.stream.Collectors;

public class FeedbackService extends AbstractService {

    public FeedbackService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public int getWaitingFeedbackCounter(String session) {
        DoGetWaitingFeedbacksCountRequest req = new DoGetWaitingFeedbacksCountRequest(session);
        DoGetWaitingFeedbacksCountResponse res = AllegroExecutor.execute(() -> allegro.doGetWaitingFeedbacksCount(req));
        return res.getFeedbackCount();
    }

    public Observable<WaitingFeedback> getWaitingFeedbacks(String session) {
        return Observable.from(() -> new FeedbackIterator(session, 0));
    }

    public List<CreatedFeedback> createFeedback(List<CreateFeedback> feedbacks) {
        //TODO
        return null;
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
                    .collect(Collectors.toList());
        }

        @Override
        protected long getItemId(WaitingFeedback journal) {
            this.startingPoint += 200;
            return startingPoint;
        }
    }

}
