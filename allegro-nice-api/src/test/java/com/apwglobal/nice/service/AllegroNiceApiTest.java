package com.apwglobal.nice.service;

import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.login.AbstractLoggedServiceBaseTest;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
    public void shouldReturnListOfClientsData() {
        api
                .login()
                .getJournal(0)
                .limit(10)
                .forEach(j -> assertNotNull(api.getClientsDate(j.getItemId())));
    }

    @Test
    public void shouldResturnPostBuyFormsForGivenDeals() {
        api.login();
        Observable<Deal> deals = api.getDeals(0);

        api.getPostBuyForms(deals)
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

        List<NewAuctionField> fields = createNewAuctionFields();

        NewAuctionPrice res = api
                .login()
                .checkNewAuction(fields);
        assertNotNull(res.getPrice());
    }

    @Test
    public void shouldCreateNewAuctionAndChangeQty() throws IOException {
        List<NewAuctionField> fields = createNewAuctionFields();

        CreatedAuction auction = api
                .login()
                .createNewAuction(fields);
        assertNotNull(auction);

        ChangedQty changedQty = api.changeQty(auction.getItemId(), 5);
        assertEquals(5, changedQty.getLeft());
    }

    private List<NewAuctionField> createNewAuctionFields() throws IOException {
        InputStream is = getClass().getResourceAsStream("/resources/test.png");
        byte[] img = Base64.getEncoder().encode(IOUtils.toByteArray(is));

        return Arrays.asList(
                new NewAuctionField(1, FieldType.Type.STRING, System.nanoTime() + ": Testing 123"),      //title
                new NewAuctionField(2, FieldType.Type.INTEGER, 76661),                                   //category
                new NewAuctionField(4, FieldType.Type.INTEGER, 99),                                      //duration
                new NewAuctionField(29, FieldType.Type.INTEGER, 1),                                      //sell type, shop
                new NewAuctionField(5, FieldType.Type.INTEGER, 10),                                      //qty
                new NewAuctionField(8, FieldType.Type.FLOAT, 1.99f),                                     //price, buy now
                new NewAuctionField(9, FieldType.Type.INTEGER, 1),                                       //country
                new NewAuctionField(10, FieldType.Type.INTEGER, 7),                                      //state, mazowieckie
                new NewAuctionField(11, FieldType.Type.STRING, "Warszawa"),                              //city
                new NewAuctionField(12, FieldType.Type.INTEGER, 1),                                      //transport, paid by buyer
                new NewAuctionField(14, FieldType.Type.INTEGER, 32),                                     //invoice possible
                new NewAuctionField(16, FieldType.Type.IMAGE, img),                                      //image
                new NewAuctionField(24, FieldType.Type.STRING, "This is description <b>with html</b>"),  //desc
                new NewAuctionField(28, FieldType.Type.INTEGER, 0),                                      //unit, pcs
                new NewAuctionField(32, FieldType.Type.STRING, "01-234"),                                //zip
                new NewAuctionField(43, FieldType.Type.FLOAT, 7.99f),                                    //price for letter
                new NewAuctionField(44, FieldType.Type.FLOAT, 14.99f),                                   //price for courier
                new NewAuctionField(143, FieldType.Type.FLOAT, 0f),                                      //price for letter, next pcs
                new NewAuctionField(144, FieldType.Type.FLOAT, 0f),                                      //price for courier, next pcs
                new NewAuctionField(243, FieldType.Type.INTEGER, 50),                                    //qty in letter
                new NewAuctionField(244, FieldType.Type.INTEGER, 250),                                   //qty in parcel
                new NewAuctionField(340, FieldType.Type.INTEGER, 1),                                     //sending time
                new NewAuctionField(3110, FieldType.Type.INTEGER, 1)                                     //color
                //3120 - shape
        );
    }

}
