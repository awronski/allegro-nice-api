package com.apwglobal.nice.domain;

import java.util.List;

public class NewAuction {

    private List<AuctionField> fields;
    private SalesConditions salesConditions;

    public NewAuction(List<AuctionField> fields, SalesConditions cond) {
        this.fields = fields;
        this.salesConditions = cond;
    }

    public List<AuctionField> getFields() {
        return fields;
    }

    public SalesConditions getSalesConditions() {
        return salesConditions;
    }

}
