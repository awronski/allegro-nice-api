package com.apwglobal.nice.service;

import com.apwglobal.nice.domain.Category;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.FormField;
import com.apwglobal.nice.login.AbstractLoggedServiceBaseTest;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertFalse;
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

    @Test
    public void shouldReturnCountries() {
        assertFalse(api.getCountries().isEmpty());
    }

//    doesn not work in allegro test environment
//    @Test
//    public void shouldReturnAllegroMessages()  {
//        assertTrue(api.getAllMessages(LocalDateTime.now().minusDays(1000)).size() > 0);
//    }

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
}
