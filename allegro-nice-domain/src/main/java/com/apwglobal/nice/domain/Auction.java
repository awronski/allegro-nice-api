package com.apwglobal.nice.domain;

import com.apwglobal.bd.BD;
import com.apwglobal.nice.util.UnixDate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Auction {

    private long id;
    private String title;
    private String thumbnailUrl;
    private int startQuantity;
    private int soldQuantity;
    private ItemQuantityType quantityType;
    private Date startTime;
    private Optional<Date> endTime;
    private int biddersCounter;
    private int categoryId;
    private int watchersCounter;
    private int viewsCounter;
    private String note;
    private boolean special;
    private boolean shop;
    private boolean payu;
    private int extraOptions;

    private double price;
    private ItemPriceType priceType;
    private boolean open;

    private long sellerId;

    public Auction() { }

    private Auction(Builder builder) {
        id = builder.id;
        title = builder.title;
        thumbnailUrl = builder.thumbnailUrl;
        startQuantity = builder.startQuantity;
        soldQuantity = builder.soldQuantity;
        quantityType = builder.quantityType;
        startTime = builder.startTime;
        endTime = builder.endTime;
        biddersCounter = builder.biddersCounter;
        categoryId = builder.categoryId;
        watchersCounter = builder.watchersCounter;
        viewsCounter = builder.viewsCounter;
        note = builder.note;
        special = builder.special;
        shop = builder.shop;
        payu = builder.payu;
        extraOptions = builder.extraOptions;
        price = builder.price;
        priceType = builder.priceType;
        open = builder.open;
        sellerId = builder.sellerId;
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public int getStartQuantity() {
        return startQuantity;
    }
    public int getSoldQuantity() {
        return soldQuantity;
    }
    public ItemQuantityType getQuantityType() {
        return quantityType;
    }
    public Date getStartTime() {
        return startTime;
    }
    public Optional<Date> getEndTime() {
        return endTime;
    }
    public int getBiddersCounter() {
        return biddersCounter;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public int getWatchersCounter() {
        return watchersCounter;
    }
    public int getViewsCounter() {
        return viewsCounter;
    }
    public String getNote() {
        return note;
    }
    public boolean isSpecial() {
        return special;
    }
    public boolean isShop() {
        return shop;
    }
    public boolean isPayu() {
        return payu;
    }
    public int getExtraOptions() {
        return extraOptions;
    }
    public List<ExtraOptionsType> getExtraOptionsList() {
        return ExtraOptionsType.getOptions(extraOptions);
    }
    public double getPrice() {
        return price;
    }
    public ItemPriceType getPriceType() {
        return priceType;
    }
    public boolean isOpen() {
        return open;
    }
    public int getLeftQty() {
        return startQuantity - soldQuantity;
    }
    public long getSellerId() {
        return sellerId;
    }

    public static final class Builder {
        private long id;
        private String title;
        private String thumbnailUrl;
        private int startQuantity;
        private int soldQuantity;
        private ItemQuantityType quantityType;
        private Date startTime;
        private Optional<Date> endTime;
        private int biddersCounter;
        private int categoryId;
        private int watchersCounter;
        private int viewsCounter;
        private String note;
        private boolean special;
        private boolean shop;
        private boolean payu;
        private int extraOptions;
        private double price;
        private ItemPriceType priceType;
        private boolean open;
        private long sellerId;

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

        public Builder thumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder startQuantity(int startQuantity) {
            this.startQuantity = startQuantity;
            return this;
        }

        public Builder soldQuantity(int soldQuantity) {
            this.soldQuantity = soldQuantity;
            return this;
        }

        public Builder quantityType(int quantityType) {
            this.quantityType = ItemQuantityType.VALUES.get(quantityType);
            return this;
        }

        public Builder startTime(long startTime) {
            this.startTime = UnixDate.toDate(startTime);
            return this;
        }

        public Builder endTime(long endTime) {
            if (endTime == 0) {
                this.endTime = Optional.empty();
            } else {
                this.endTime = Optional.of(UnixDate.toDate(endTime));
            }
            return this;
        }

        public Builder biddersCounter(int biddersCounter) {
            this.biddersCounter = biddersCounter;
            return this;
        }

        public Builder categoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder watchersCounter(int watchersCounter) {
            this.watchersCounter = watchersCounter;
            return this;
        }

        public Builder viewsCounter(int viewsCounter) {
            this.viewsCounter = viewsCounter;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder special(int special) {
            this.special = special == 1;
            return this;
        }

        public Builder shop(int shop) {
            this.shop = shop == 1;
            return this;
        }

        public Builder payu(int payu) {
            this.payu = payu == 1;
            return this;
        }

        public Builder extraOptions(int extraOptions) {
            this.extraOptions = extraOptions;
            return this;
        }

        public Builder price(float price) {
            this.price = new BD(price).doubleValue();
            return this;
        }

        public Builder priceType(int priceType) {
            this.priceType = ItemPriceType.VALUES.get(priceType);
            return this;
        }

        public Builder open(boolean open) {
            this.open = open;
            return this;
        }

        public Builder sellerId(long sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public Auction build() {
            return new Auction(this);
        }
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", startQuantity=" + startQuantity +
                ", soldQuantity=" + soldQuantity +
                ", vewsCounter=" + viewsCounter +
                ", special=" + special +
                ", shop=" + shop +
                ", payu=" + payu +
                ", extraOptions=" + extraOptions +
                ", price=" + price +
                ", open=" + open +
                '}';
    }
}
