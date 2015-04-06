package com.apwglobal.nice.shop;

import com.apwglobal.nice.domain.ShopCategory;
import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import pl.allegro.webapi.CatInfoType;
import pl.allegro.webapi.DoGetShopCatsDataRequest;
import pl.allegro.webapi.DoGetShopCatsDataResponse;
import pl.allegro.webapi.ServicePort;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class ShopService extends AbstractService {


    public ShopService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,97#method-output
     */
    public List<ShopCategory> getShopCategories(String session) {
        DoGetShopCatsDataRequest req = new DoGetShopCatsDataRequest(session);
        DoGetShopCatsDataResponse res = AllegroExecutor.execute(() -> allegro.doGetShopCatsData(req));

        List<ShopCategory> categories = convToShopCategories(res);
        return fillCategoriesFields(categories, getCatIdShopCategoryMap(res, categories));
    }

    private List<ShopCategory> convToShopCategories(DoGetShopCatsDataResponse cats) {
        return cats.getShopCatsList().getItem()
                .stream()
                .map(i -> new ShopCategory.Builder()
                        .id(i.getCatId())
                        .name(i.getCatName())
                        .build())
                .collect(toList());
    }

    private Map<Integer, Optional<ShopCategory>> getCatIdShopCategoryMap(DoGetShopCatsDataResponse res, List<ShopCategory> categories) {
        Map<Integer, ShopCategory> catIdShopCategoryMap = categories
                .stream()
                .collect(toMap(ShopCategory::getId, c -> c));

        return res.getShopCatsList().getItem()
                .stream()
                .collect(toMap(CatInfoType::getCatId, c -> getParent(catIdShopCategoryMap, c)));
    }

    private Optional<ShopCategory> getParent(Map<Integer, ShopCategory> catIdShopCategoryMap, CatInfoType c) {
        return ofNullable(catIdShopCategoryMap.get(c.getCatParent()));
    }

    private List<ShopCategory> fillCategoriesFields(List<ShopCategory> categories, Map<Integer, Optional<ShopCategory>> catsParents) {
        categories
                .stream()
                .forEach(c -> c.setParent(catsParents.get(c.getId())));

        categories
                .stream()
                .filter(c -> c.getParent().isPresent())
                .forEach(c -> c.getParent().get().addChild(c));

        return categories;
    }

}
