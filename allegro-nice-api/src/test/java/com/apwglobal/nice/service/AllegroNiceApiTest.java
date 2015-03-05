package com.apwglobal.nice.service;

import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.Journal;
import com.apwglobal.nice.login.AbstractLoggedServiceBaseTest;
import org.junit.Assert;
import org.junit.Test;
import pl.allegro.webapi.ItemPostBuyDataStruct;
import rx.Observable;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

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

//    doesn not work in allegro test environment
//    @Test
//    public void shouldReturnAllegroMessages()  {
//        assertTrue(api.getAllMessages(LocalDateTime.now().minusDays(1000)).size() > 0);
//    }

    @Test
    public void shouldReturnListOfClientsData() {
        api.login();
        Observable<Journal> o = api.getJournal(0);
        List<Journal> jourlans = o.limit(10)
                .toList()
                .toBlocking()
                .single();

        assertNotNull(jourlans);

        List<ItemPostBuyDataStruct> postBuy = jourlans
                .stream()
                .flatMap(j -> api.getClientsDate(j.getItemId()).stream())
                .collect(Collectors.toList());

        assertNotNull(postBuy);
    }

    @Test
    public void shouldResturnListOfDealsWithFilledBuyersForms() {
        api.login();
        List<Deal> deals = api.getDeals(0)
                .toList()
                .toBlocking()
                .single();

        api.fillBuyersForms(deals);
        deals
                .stream()
                .forEach(d -> assertNotNull(d.getPostBuyFormDataStruct()));
    }

    @Test
    public void shouldReturnListOfAllSellersAuctions() {
        api.login();
        List<Auction> auctions = api.getAuctions()
                .toList()
                .toBlocking()
                .single();

        auctions
                .stream()
                .forEach(Assert::assertNotNull);

    }
}
