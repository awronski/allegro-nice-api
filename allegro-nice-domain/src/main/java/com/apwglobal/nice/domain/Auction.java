package com.apwglobal.nice.domain;

import com.apwglobal.nice.util.UnixDate;

import java.util.Date;
import java.util.Optional;

public class Auction {

    private long itemId;
    private String itemTitle;
    private String itemThumbnailUrl;
    private int itemStartQuantity;
    private int itemSoldQuantity;
    private ItemQuantityType itemQuantityType;
    private Date itemStartTime;
    private Optional<Date> itemEndTime;
    private int itemBiddersCounter;
    private int itemCategoryId;
    private int itemWatchersCounter;
    private int itemViewsCounter;
    private String itemNote;
    private boolean special;
    private boolean shop;
    private boolean payu;

    private double price;
    private ItemPriceType priceType;

    public Auction() { }

    private Auction(Builder builder) {
        itemId = builder.itemId;
        itemTitle = builder.itemTitle;
        itemThumbnailUrl = builder.itemThumbnailUrl;
        itemStartQuantity = builder.itemStartQuantity;
        itemSoldQuantity = builder.itemSoldQuantity;
        itemQuantityType = builder.itemQuantityType;
        itemStartTime = builder.itemStartTime;
        itemEndTime = builder.itemEndTime;
        itemBiddersCounter = builder.itemBiddersCounter;
        itemCategoryId = builder.itemCategoryId;
        itemWatchersCounter = builder.itemWatchersCounter;
        itemViewsCounter = builder.itemViewsCounter;
        itemNote = builder.itemNote;
        special = builder.special;
        shop = builder.shop;
        payu = builder.payu;
        price = builder.price;
        priceType = builder.priceType;
    }

    public long getItemId() {
        return itemId;
    }
    public String getItemTitle() {
        return itemTitle;
    }
    public String getItemThumbnailUrl() {
        return itemThumbnailUrl;
    }
    public int getItemStartQuantity() {
        return itemStartQuantity;
    }
    public int getItemSoldQuantity() {
        return itemSoldQuantity;
    }
    public ItemQuantityType getItemQuantityType() {
        return itemQuantityType;
    }
    public Date getItemStartTime() {
        return itemStartTime;
    }
    public Optional<Date> getItemEndTime() {
        return itemEndTime;
    }
    public int getItemBiddersCounter() {
        return itemBiddersCounter;
    }
    public int getItemCategoryId() {
        return itemCategoryId;
    }
    public int getItemWatchersCounter() {
        return itemWatchersCounter;
    }
    public int getItemViewsCounter() {
        return itemViewsCounter;
    }
    public String getItemNote() {
        return itemNote;
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
    public double getPrice() {
        return price;
    }
    public ItemPriceType getPriceType() {
        return priceType;
    }

    public static final class Builder {
        private long itemId;
        private String itemTitle;
        private String itemThumbnailUrl;
        private int itemStartQuantity;
        private int itemSoldQuantity;
        private ItemQuantityType itemQuantityType;
        private Date itemStartTime;
        private Optional<Date> itemEndTime;
        private int itemBiddersCounter;
        private int itemCategoryId;
        private int itemWatchersCounter;
        private int itemViewsCounter;
        private String itemNote;
        private boolean special;
        private boolean shop;
        private boolean payu;
        private double price;
        private ItemPriceType priceType;

        public Builder() {
        }

        public Builder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder itemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
            return this;
        }

        public Builder itemThumbnailUrl(String itemThumbnailUrl) {
            this.itemThumbnailUrl = itemThumbnailUrl;
            return this;
        }

        public Builder itemStartQuantity(int itemStartQuantity) {
            this.itemStartQuantity = itemStartQuantity;
            return this;
        }

        public Builder itemSoldQuantity(int itemSoldQuantity) {
            this.itemSoldQuantity = itemSoldQuantity;
            return this;
        }

        public Builder itemQuantityType(int itemQuantityType) {
            this.itemQuantityType = ItemQuantityType.VALUES.get(itemQuantityType);
            return this;
        }

        public Builder itemStartTime(long itemStartTime) {
            this.itemStartTime = UnixDate.toDate(itemStartTime);
            return this;
        }

        public Builder itemEndTime(long itemEndTime) {
            if (itemEndTime == 0) {
                this.itemEndTime = Optional.empty();
            } else {
                this.itemEndTime = Optional.of(UnixDate.toDate(itemEndTime));
            }
            return this;
        }

        public Builder itemBiddersCounter(int itemBiddersCounter) {
            this.itemBiddersCounter = itemBiddersCounter;
            return this;
        }

        public Builder itemCategoryId(int itemCategoryId) {
            this.itemCategoryId = itemCategoryId;
            return this;
        }

        public Builder itemWatchersCounter(int itemWatchersCounter) {
            this.itemWatchersCounter = itemWatchersCounter;
            return this;
        }

        public Builder itemViewsCounter(int itemViewsCounter) {
            this.itemViewsCounter = itemViewsCounter;
            return this;
        }

        public Builder itemNote(String itemNote) {
            this.itemNote = itemNote;
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

        public Builder price(float price) {
            this.price = price;
            return this;
        }

        public Builder priceType(int priceType) {
            this.priceType = ItemPriceType.VALUES.get(priceType);
            return this;
        }

        public Auction build() {
            return new Auction(this);
        }
    }

    @Override
    public String toString() {
        return "Auction{" +
                "itemId=" + itemId +
                ", itemStartQuantity=" + itemStartQuantity +
                ", itemSoldQuantity=" + itemSoldQuantity +
                ", itemViewsCounter=" + itemViewsCounter +
                ", special=" + special +
                ", shop=" + shop +
                ", payu=" + payu +
                ", price=" + price +
                '}';
    }
}
