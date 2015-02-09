package com.apwglobal.nice.service;

import com.apwglobal.nice.journal.Journal;
import com.apwglobal.nice.login.AbstractLoggedServiceBaseTest;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AllegroNiceApiTest extends AbstractLoggedServiceBaseTest {

    @Test
    public void shouldNotReturnSessionId() {
        Assert.assertNull(api.getSession());
    }

    @Test
    public void shouldReturnSessionId() {
        assertNotNull(api.login().getSession());
    }

    @Test
    public void shouldReturnStatus() {
        assertNotNull(api.getStatus());
    }

    @Test
    public void shouldReturnAllegroMessages()  {
        assertTrue(api.getAllMessages(LocalDateTime.now().minusDays(1000)).size() > 0);
    }

    @Test
    public void testGetLastSiteJournalEventId() {
        api.login();
        Observable<Journal> o = api.getJournal(0);
        o.limit(10)
                .toBlocking()
                .getIterator()
                .forEachRemaining(Assert::assertNotNull);

        assertNotNull(o);
    }
}
