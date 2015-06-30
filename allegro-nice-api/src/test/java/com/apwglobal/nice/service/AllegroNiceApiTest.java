package com.apwglobal.nice.service;

import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.login.AbstractLoggedServiceBaseTest;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class AllegroNiceApiTest extends AbstractLoggedServiceBaseTest {

    @Test
    public void shouldReturnSessionId() {
        assertNotNull(api.login().getSession());
    }

    @Test
    public void shouldReturnStatus() {
        assertNotNull(api.getStatus());
    }

    @Test
    public void shouldReturnCountries() {
        assertFalse(api.getCountries().isEmpty());
    }

    @Test
    public void shouldReturnShippment() {
        Map<Integer, String> shippment = api.getShippment();
        assertFalse(shippment.isEmpty());
    }

    @Test
    public void shouldReturnListOfClientsData() {
        api
                .login()
                .getJournal(0)
                .limit(10)
                .forEach(j -> assertNotNull(api.getClientsDate(j.getItemId())));
    }

    @Test
    public void shouldResturnPaymentsForGivenDeals() {
        api.login();
        Observable<Deal> deals = api.getDeals(0);

        Observable<Payment> payments = api.getPayments(deals);
        payments
                .forEach(f -> assertNotNull(f.getEmail()));
    }

    @Test
    public void shouldReturnListOfAllSellersAuctions() {
        api
                .login()
                .getAuctions()
                .forEach(Assert::assertNotNull);
    }

    @Test
    public void shouldReturnAuctionFields() {
        List<Auction> single = api.login().getAuctions().limit(1).toList().toBlocking().single();
        if (!single.isEmpty()) {
            List<AuctionField> auctionFields = api
                    .login()
                    .getAuctionFields(single.get(0).getId());
            assertNotNull(auctionFields);
        }
    }
    @Test
    public void shouldChangeAuction() {
        List<Auction> single = api.login().getAuctions().limit(1).toList().toBlocking().single();
        if (!single.isEmpty()) {
            List<AuctionField> fields = Collections.singletonList(
                    new AuctionField<>(1, FieldType.Type.STRING, System.nanoTime() + ": Testing 123")
            );
            ChangedAuctionInfo changedAuctionInfo = api
                    .login()
                    .changeAuctions(single.get(0).getId(), fields);
            assertNotNull(changedAuctionInfo);
        }
    }
    @Test
    public void shouldReturnListCategories() {
        List<Category> categories = api
                .login()
                .getCategories();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());

    }

    @Test
    public void shouldReturnSellFormFields() {
        List<FormField> fields = api
                .login()
                .getSellFormFields(76661)
                .stream()
                .filter(FormField::isRequired)
                .collect(toList());

        assertFalse(fields.isEmpty());
    }

    @Test
    public void shouldReturnPriceForAuction() throws IOException {

        List<AuctionField> fields = createNewAuctionFields();

        NewAuctionPrice res = api
                .login()
                .checkNewAuction(fields);
        assertNotNull(res.getPrice());
    }

    @Test
    public void shouldCreateNewAuctionAndChangeQty() throws IOException {
        List<AuctionField> fields = createNewAuctionFields();

        CreatedAuction auction = api
                .login()
                .createNewAuction(fields);
        assertNotNull(auction);

        ChangedQty changedQty = api.changeQty(auction.getItemId(), 5);
        assertEquals(5, changedQty.getLeft());
    }

    @Test
    public void shouldCreateNewAuctionAndThanFinishIt() throws IOException {
        List<AuctionField> fields = createNewAuctionFields();

        CreatedAuction auction = api
                .login()
                .createNewAuction(fields);
        assertNotNull(auction);

        List<FinishAuctionFailure> failures = api.finishAuctions(singletonList(auction.getItemId()));
        assertTrue(failures.isEmpty());
    }

    private List<AuctionField> createNewAuctionFields() throws IOException {
        InputStream is = getClass().getResourceAsStream("/resources/test.png");
        String img = Base64.getEncoder().encodeToString(IOUtils.toByteArray(is));

        return Arrays.asList(
                new AuctionField(FieldId.TITLE, FieldType.Type.STRING, System.nanoTime() + ": Testing 123"),
                new AuctionField(FieldId.CATEGORY, FieldType.Type.INTEGER, 76661),
                new AuctionField(FieldId.DURATION, FieldType.Type.INTEGER, 99),
                new AuctionField(FieldId.SELL_TYPE, FieldType.Type.INTEGER, 1),
                new AuctionField(FieldId.QTY, FieldType.Type.INTEGER, 10),
                new AuctionField(FieldId.PRICE, FieldType.Type.FLOAT, 1.99f),
                new AuctionField(FieldId.COUNTRY, FieldType.Type.INTEGER, 1),
                new AuctionField(FieldId.STATE, FieldType.Type.INTEGER, 7),
                new AuctionField(FieldId.CITY, FieldType.Type.STRING, "Warszawa"),
                new AuctionField(FieldId.TRANSPORT_PAID_BY, FieldType.Type.INTEGER, 1),
                new AuctionField(FieldId.INVOICE, FieldType.Type.INTEGER, 32),
                new AuctionField(FieldId.IMAGE, FieldType.Type.IMAGE, img),
                new AuctionField(FieldId.DESC, FieldType.Type.STRING, "This is description <b>with html</b>"),
                new AuctionField(FieldId.UNIT, FieldType.Type.INTEGER, 0),
                new AuctionField(FieldId.ZIP, FieldType.Type.STRING, "01-234"),
                new AuctionField(FieldId.PRICE_FOR_LETTER, FieldType.Type.FLOAT, 7.99f),
                new AuctionField(FieldId.PRICE_FOR_COURIER, FieldType.Type.FLOAT, 14.99f),
                new AuctionField(FieldId.PRICE_FOR_LETTER_NEXT_UNIT, FieldType.Type.FLOAT, 0f),
                new AuctionField(FieldId.PRICE_FOR_COURIER_NEXT_UNIT, FieldType.Type.FLOAT, 0f),
                new AuctionField(FieldId.MAX_QTY_IN_LETTER, FieldType.Type.INTEGER, 50),
                new AuctionField(FieldId.MAX_QTY_IN_COURIER, FieldType.Type.INTEGER, 250),
                new AuctionField(FieldId.SENDING_TIME, FieldType.Type.INTEGER, 1),
                new AuctionField(FieldId.COLOR, FieldType.Type.INTEGER, 1)
        );
    }

    @Test
    public void shouldReturnWaitingFeedbackCounter() {
        int counter = api.login()
                .getWaintingFeedbackCounter();

        assertTrue(counter >= 0);
    }

    @Test
    public void shouldReturnWaitingFeedbacks() {
        Observable<WaitingFeedback> feedbacks = api.login()
                .getWaitingFeedbacks();

        feedbacks
                .forEach(f -> assertNotNull(f.getItemId()));
    }

    @Test
    public void shouldReturnIncomingPayments() {
        List<IncomingPayment> incomingPayments = api.login()
                .getIncomingPayments()
                .toList()
                .toBlocking()
                .single();

        assertNotNull(incomingPayments);
    }

}
