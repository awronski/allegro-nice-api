package com.apwglobal.nice.domain;

public class NewAuctionPrice {

    protected String price;
    protected String priceDesc;
    protected boolean allegroStandard;

    public NewAuctionPrice() { }

    private NewAuctionPrice(Builder builder) {
        price = builder.price;
        priceDesc = builder.priceDesc;
        allegroStandard = builder.allegroStandard;
    }

    public String getPrice() {
        return price;
    }
    public String getPriceDesc() {
        return priceDesc;
    }
    public boolean isAllegroStandard() {
        return allegroStandard;
    }

    public static final class Builder {
        private String price;
        private String priceDesc;
        private boolean allegroStandard;

        public Builder() {
        }

        public Builder price(String price) {
            this.price = price;
            return this;
        }

        public Builder priceDesc(String priceDesc) {
            this.priceDesc = priceDesc;
            return this;
        }

        public Builder allegroStandard(int allegroStandard) {
            this.allegroStandard = allegroStandard == 1;
            return this;
        }

        public NewAuctionPrice build() {
            return new NewAuctionPrice(this);
        }
    }

    @Override
    public String toString() {
        return "NewAuctionPrice{" +
                "price='" + price + '\'' +
                ", priceDesc='" + priceDesc + '\'' +
                ", allegroStandard=" + allegroStandard +
                '}';
    }
}
