package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.NewAuctionField;
import pl.allegro.webapi.FieldsValue;

public class NewAuctionFieldConv {


    // 1 - string,
    // 2 - integer,
    // 3 - float,
    // 7 - image (base64Binary),
    // 9 - datetime (Unix time),
    // 13 - date (dd-mm-yyyy)
    public static FieldsValue convert(NewAuctionField f) {
        FieldsValue fv = new FieldsValue();
        fv.setFid(f.getId());
        switch (f.getType()) {
            case STRING:
                fv.setFvalueString((String) f.getValue());
                break;

            case INTEGER:
                fv.setFvalueInt((Integer) f.getValue());
                break;

            case FLOAT:
                fv.setFvalueFloat((Float) f.getValue());
                break;

            case IMAGE:
                fv.setFvalueImage((byte[]) f.getValue());
                break;

            case UNIX_DATE:
                fv.setFvalueDatetime((Float) f.getValue());
                break;

            case DATE:
                fv.setFvalueDate((String) f.getValue());
                break;
        }
        return fv;
    }

}
