package com.apwglobal.nice.conv;

import com.apwglobal.bd.BD;
import com.apwglobal.nice.domain.AuctionField;
import com.apwglobal.nice.domain.FieldType;
import pl.allegro.webapi.ArrayOfFieldsvalue;
import pl.allegro.webapi.FieldsValue;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AuctionFieldConv {


    // 1 - string,
    // 2 - integer,
    // 3 - float,
    // 7 - image (base64Binary),
    // 9 - datetime (Unix time),
    // 13 - date (dd-mm-yyyy)
    public static FieldsValue convert(AuctionField f) {
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
                if (f.getValue() instanceof Float) {
                    fv.setFvalueFloat((Float) f.getValue());
                } else {
                    fv.setFvalueFloat(new BD((Double) f.getValue()).floatValue());
                }
                break;

            case IMAGE:
                fv.setFvalueImage(((String) f.getValue()).getBytes());
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

    public static AuctionField convert(FieldsValue f) {

        AuctionField<?> af;
        if (!f.getFvalueString().isEmpty()) {
            af = new AuctionField<>(f.getFid(), FieldType.Type.STRING, f.getFvalueString());
        } else if (f.getFvalueDatetime() > 0) {
            af = new AuctionField<>(f.getFid(), FieldType.Type.UNIX_DATE, f.getFvalueDatetime());
        } else if (!f.getFvalueDate().isEmpty()) {
            af = new AuctionField<>(f.getFid(), FieldType.Type.DATE, f.getFvalueDate());
        } else if (f.getFvalueImage().length > 0) {
            af = new AuctionField<>(f.getFid(), FieldType.Type.IMAGE, f.getFvalueImage());
        } else if (f.getFvalueInt() > 0) {
            af = new AuctionField<>(f.getFid(), FieldType.Type.INTEGER, f.getFvalueInt());
        } else {
            //just a guess
            af = new AuctionField<>(f.getFid(), FieldType.Type.FLOAT, f.getFvalueFloat());
        }

        return af;
    }

    public static ArrayOfFieldsvalue convert(List<AuctionField> fields) {
        return new ArrayOfFieldsvalue(
                fields
                        .stream()
                        .map(AuctionFieldConv::convert)
                        .collect(toList())
        );
    }

    public static List<AuctionField> convert(ArrayOfFieldsvalue values) {
        return values.getItem()
                .stream()
                .map(AuctionFieldConv::convert)
                .collect(toList());
    }
}
