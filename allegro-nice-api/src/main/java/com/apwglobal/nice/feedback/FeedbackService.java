package com.apwglobal.nice.feedback;

import com.apwglobal.nice.conv.FeedbackConv;
import com.apwglobal.nice.domain.Feedback;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractAllegroIterator;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.DoGetWaitingFeedbacksCountRequest;
import pl.allegro.webapi.DoGetWaitingFeedbacksCountResponse;
import pl.allegro.webapi.DoGetWaitingFeedbacksRequest;
import pl.allegro.webapi.ServicePort;
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

    public Observable<Feedback> getWaitingFeedbacks(String session) {
        return Observable.from(() -> new FeedbackIterator(session, 0));
    }

    private class FeedbackIterator extends AbstractAllegroIterator<Feedback> {

        public FeedbackIterator(String session, long startingPoint) {
            super(session, startingPoint);
        }

        @Override
        protected List<Feedback> doFetch() {
            DoGetWaitingFeedbacksRequest req = new DoGetWaitingFeedbacksRequest(session, (int) startingPoint, 200);
            return AllegroExecutor.execute(() -> allegro.doGetWaitingFeedbacks(req)).getFeWaitList().getItem()
                    .stream()
                    .map(FeedbackConv::convert)
                    .collect(Collectors.toList());
        }

        @Override
        protected long getItemId(Feedback journal) {
            this.startingPoint += 200;
            return startingPoint;
        }
    }

}
