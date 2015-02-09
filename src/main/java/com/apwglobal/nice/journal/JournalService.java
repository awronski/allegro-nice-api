package com.apwglobal.nice.journal;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.DoGetSiteJournalInfoRequest;
import pl.allegro.webapi.DoGetSiteJournalInfoResponse;
import pl.allegro.webapi.DoGetSiteJournalRequest;
import pl.allegro.webapi.ServicePort;
import rx.Observable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class JournalService extends AbstractService {

    public JournalService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    private DoGetSiteJournalInfoResponse getNumberOfEvents(String session, long startingPoing, EventType type) {
        DoGetSiteJournalInfoRequest request = new DoGetSiteJournalInfoRequest(session, startingPoing, type.getType());
        return AllegroExecutor.execute(() -> allegro.doGetSiteJournalInfo(request));
    }

    public Observable<Journal> getJournal(String session, long startingPoint) {
        return Observable.from(() -> new JournalIterator(session, startingPoint));
    }

    private class JournalIterator implements Iterator<Journal> {

        private String session;
        private long startingPoint;
        private List<Journal> journals;
        private boolean fetched;
        private int index;

        public JournalIterator(String session, long startingPoint) {
            this.session = session;
            this.startingPoint = startingPoint;
            this.journals = new ArrayList<>();
        }

        private boolean isFetchNeeded() {
            return !fetched || index == journals.size();
        }

        @Override
        public boolean hasNext() {
            if (isFetchNeeded()) {
                fetch();
            }
            return index < journals.size();
        }

        @Override
        public Journal next() {
            if (isFetchNeeded()) {
                fetch();
            }
            return journals.get(index++);
        }

        private void fetch() {
            this.fetched = true;
            this.startingPoint = getStartingPoint();
            this.index = 0;

            DoGetSiteJournalRequest request = new DoGetSiteJournalRequest(session, startingPoint, EventType.USER.getType());
            this.journals = AllegroExecutor.execute(() -> allegro.doGetSiteJournal(request)).getSiteJournalArray().getItem()
                    .stream()
                    .map(sj -> new Journal.Builder()
                            .rowId(sj.getRowId())
                            .itemId(sj.getItemId())
                            .currentPrice(sj.getCurrentPrice())
                            .changeType(sj.getChangeType())
                            .changeDate(sj.getChangeDate())
                            .build())
                    .sorted((j1, j2) -> Long.valueOf(j1.getRowId()).compareTo(j2.getRowId()))
                    .collect(Collectors.toList());
        }

        private long getStartingPoint() {
            return journals.size() == 0
                    ? startingPoint
                    : journals.get(journals.size() - 1).getRowId();
        }
    }
}
