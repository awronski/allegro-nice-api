package com.apwglobal.nice.domain;

import com.apwglobal.bd.BD;

public class Item {

    private long id;
    private long transactionId;
    private String title;

    private double price;
    private int quantity;
    private double amount;

    public Item() { }

    private Item(Builder builder) {
        id = builder.id;
        transactionId = builder.transactionId;
        title = builder.title;
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
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getAmount() {
        return amount;
    }
    public long getTransactionId() {
        return transactionId;
    }

    public static final class Builder {
        private long id;
        private long transactionId;
        private String title;
        private double price;
        private int quantity;
        private double amount;

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder transactionId(long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder price(float price) {
            this.price = new BD(price).doubleValue();
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder amount(float amount) {
            this.amount = new BD(amount).doubleValue();
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
                ", transactionId='" + transactionId + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }

}
