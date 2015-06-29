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
    public void shouldChangeAuction() {
        List<Auction> single = api.login().getAuctions().limit(1).toList().toBlocking().single();
        if (!single.isEmpty()) {
            List<AuctionField> fields = Collections.singletonList(
                    new AuctionField(1, FieldType.Type.STRING, System.nanoTime() + ": Testing 123")
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
                new AuctionField(1, FieldType.Type.STRING, System.nanoTime() + ": Testing 123"),      //title
                new AuctionField(2, FieldType.Type.INTEGER, 76661),                                   //category
                new AuctionField(4, FieldType.Type.INTEGER, 99),                                      //duration
                new AuctionField(29, FieldType.Type.INTEGER, 1),                                      //sell type, shop
                new AuctionField(5, FieldType.Type.INTEGER, 10),                                      //qty
                new AuctionField(8, FieldType.Type.FLOAT, 1.99f),                                     //price, buy now
                new AuctionField(9, FieldType.Type.INTEGER, 1),                                       //country
                new AuctionField(10, FieldType.Type.INTEGER, 7),                                      //state, mazowieckie
                new AuctionField(11, FieldType.Type.STRING, "Warszawa"),                              //city
                new AuctionField(12, FieldType.Type.INTEGER, 1),                                      //transport, paid by buyer
                new AuctionField(14, FieldType.Type.INTEGER, 32),                                     //invoice possible
                new AuctionField(16, FieldType.Type.IMAGE, img),                                      //image
                new AuctionField(24, FieldType.Type.STRING, "This is description <b>with html</b>"),  //desc
                new AuctionField(28, FieldType.Type.INTEGER, 0),                                      //unit, pcs
                new AuctionField(32, FieldType.Type.STRING, "01-234"),                                //zip
                new AuctionField(43, FieldType.Type.FLOAT, 7.99f),                                    //price for letter
                new AuctionField(44, FieldType.Type.FLOAT, 14.99f),                                   //price for courier
                new AuctionField(143, FieldType.Type.FLOAT, 0f),                                      //price for letter, next pcs
                new AuctionField(144, FieldType.Type.FLOAT, 0f),                                      //price for courier, next pcs
                new AuctionField(243, FieldType.Type.INTEGER, 50),                                    //qty in letter
                new AuctionField(244, FieldType.Type.INTEGER, 250),                                   //qty in parcel
                new AuctionField(340, FieldType.Type.INTEGER, 1),                                     //sending time
                new AuctionField(3110, FieldType.Type.INTEGER, 1)                                     //color
                //3120 - shape
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
