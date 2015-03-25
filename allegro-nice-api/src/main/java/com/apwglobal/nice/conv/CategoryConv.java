package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Category;
import pl.allegro.webapi.CatInfoType;

public class CategoryConv {

    public static Category convert(CatInfoType c) {
        return new Category.Builder()
                .catId(c.getCatId())
                .catName(c.getCatName())
                .catParent(c.getCatParent())
                .build();
    }

}
