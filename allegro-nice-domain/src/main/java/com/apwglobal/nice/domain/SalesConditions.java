package com.apwglobal.nice.domain;

public class SalesConditions {

    private String impliedWarranty;
    private String returnPolicy;
    private String warranty;

    public SalesConditions() { }

    public SalesConditions(String impliedWarranty, String returnPolicy, String warranty) {
        this.impliedWarranty = impliedWarranty;
        this.returnPolicy = returnPolicy;
        this.warranty = warranty;
    }

    public String getImpliedWarranty() {
        return impliedWarranty;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public String getWarranty() {
        return warranty;
    }

}
