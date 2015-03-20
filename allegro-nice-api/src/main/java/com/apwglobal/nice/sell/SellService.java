package com.apwglobal.nice.sell;

import com.apwglobal.nice.conv.CategoryConv;
import com.apwglobal.nice.conv.FormFieldConv;
import com.apwglobal.nice.conv.NewAuctionFieldConv;
import com.apwglobal.nice.domain.Category;
import com.apwglobal.nice.domain.FormField;
import com.apwglobal.nice.domain.NewAuctionField;
import com.apwglobal.nice.domain.NewAuctionPrice;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SellService extends AbstractService {


    public SellService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,46#method-output
     */
    public List<Category> getCategories() {
        DoGetCatsDataRequest req = new DoGetCatsDataRequest(conf.getCountryId(), 0l, cred.getKey());
        DoGetCatsDataResponse res = AllegroExecutor.execute(() -> allegro.doGetCatsData(req));
        return res.getCatsList().getItem()
                .stream()
                .map(CategoryConv::convert)
                .collect(toList());
    }

    /**
     *
     * http://allegro.pl/webapi/documentation.php/show/id,782#method-output
     */
    public List<FormField> getSellFormFields(int categoryId) {
        DoGetSellFormFieldsForCategoryRequest req = new DoGetSellFormFieldsForCategoryRequest(cred.getKey(), conf.getCountryId(), categoryId);
        DoGetSellFormFieldsForCategoryResponse res = AllegroExecutor.execute(() -> allegro.doGetSellFormFieldsForCategory(req));
        return res.getSellFormFieldsForCategory().getSellFormFieldsList().getItem()
                .stream()
                .map(FormFieldConv::convert)
                .collect(toList());
    }

    /**
     *
     * http://allegro.pl/webapi/documentation.php/show/id,41#method-output
     */
    public NewAuctionPrice checkNewAuction(List<NewAuctionField> fields, String session) {
        ArrayOfFieldsvalue values = new ArrayOfFieldsvalue(
                fields
                .stream()
                .map(NewAuctionFieldConv::convert)
                .collect(Collectors.toList())
        );
        DoCheckNewAuctionExtRequest req = new DoCheckNewAuctionExtRequest(session, values, new ArrayOfVariantstruct());
        DoCheckNewAuctionExtResponse res = AllegroExecutor.execute(() -> allegro.doCheckNewAuctionExt(req));

        return new NewAuctionPrice.Builder()
                .price(res.getItemPrice())
                .priceDesc(res.getItemPriceDesc())
                .allegroStandard(res.getItemIsAllegroStandard())
                .build();
    }
}
