package com.apwglobal.nice.domain;

public class Item {

    private long id;
    private String title;
    private int country;

    private double price;
    private int quantity;
    private double amount;

    public Item() { }

    private Item(Builder builder) {
        id = builder.id;
        title = builder.title;
        country = builder.country;
        price = builder.price;
        quantity = builder.quantity;
        amount = builder.amount;
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public int getCountry() {
        return country;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getAmount() {
        return amount;
    }

    public static final class Builder {
        private long id;
        private String title;
        private int country;
        private float price;
        private int quantity;
        private double amount;

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder country(int country) {
            this.country = country;
            return this;
        }

        public Builder price(float price) {
            this.price = price;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }

}
