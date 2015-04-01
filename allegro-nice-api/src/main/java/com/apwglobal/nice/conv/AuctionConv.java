package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Auction;
import pl.allegro.webapi.ItemPriceStruct;
import pl.allegro.webapi.SellItemStruct;

import static com.apwglobal.nice.domain.AuctionStatusType.OPEN;

public class AuctionConv {

    public static Auction convert(SellItemStruct s, ItemPriceStruct itemPriceStruct) {
        return new Auction.Builder()
                .id(s.getItemId())
                .title(s.getItemTitle())
                .thumbnailUrl(s.getItemThumbnailUrl())
                .startQuantity(s.getItemStartQuantity())
                .soldQuantity(s.getItemSoldQuantity())
                .quantityType(s.getItemQuantityType())
                .startTime(s.getItemStartTime())
                .endTime(s.getItemEndTime())
                .biddersCounter(s.getItemBiddersCounter())
                .categoryId(s.getItemCategoryId())
                .watchersCounter(s.getItemWatchersCounter())
                .viewsCounter(s.getItemViewsCounter())
                .note(s.getItemNote())
                .special(s.getItemSpecialInfo())
                .shop(s.getItemShopInfo())
                .payu(s.getItemPayuInfo())
                .price(itemPriceStruct.getPriceValue())
                .priceType(itemPriceStruct.getPriceType())
                .status(OPEN)
                .ref("?")
                .build();
    }
}
