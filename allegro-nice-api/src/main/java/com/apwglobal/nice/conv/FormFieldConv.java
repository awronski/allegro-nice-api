package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.FieldType;
import com.apwglobal.nice.domain.FormField;
import pl.allegro.webapi.SellFormType;

public class FormFieldConv {

    public static FormField conv(SellFormType t) {
        return new FormField.Builder()
                .id(t.getSellFormId())
                .required(t.getSellFormOpt())
                .title(t.getSellFormTitle())
                .desc(t.getSellFormFieldDesc())
                .type(new FieldType.Builder()
                        .type(t.getSellFormType())
                        .valueType(t.getSellFormResType())
                        .defValue(t.getSellFormDefValue())
                        .optsValues(t.getSellFormOptsValues())
                        .optValuesDesc(t.getSellFormDesc())
                        .build()
                ).build();
    }

}
