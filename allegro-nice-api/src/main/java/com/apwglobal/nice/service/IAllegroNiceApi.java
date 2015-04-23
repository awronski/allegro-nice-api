package com.apwglobal.nice.service;

import com.apwglobal.nice.domain.*;
import pl.allegro.webapi.ItemPostBuyDataStruct;
import pl.allegro.webapi.SysStatusType;
import rx.Observable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IAllegroNiceApi {

    //login
    IAllegroNiceApi login();
    AllegroSession getSession();

    //info
    Map<Integer, String> getCountries();
    Map<Integer, String> getShippment();

    //status and components
    SysStatusType getStatus();

    //messages
    List<AllegroMessage> getAllMessages(Date from);

    //journal + clients data
    Observable<Journal> getJournal(long startingPoint);
    List<ItemPostBuyDataStruct> getClientsDate(long itemId);

    //deals
    Observable<Deal> getDeals(long startingPoint);
    Observable<Payment> getPayments(Observable<Deal> deals);

    //selling
    Observable<Auction> getAuctions();
    List<Category> getCategories();
    List<FormField> getSellFormFields(int categoryId);
    NewAuctionPrice checkNewAuction(List<NewAuctionField> fields);
    CreatedAuction createNewAuction(List<NewAuctionField> fields);
    ChangedQty changeQty(long itemId, int newQty);
    List<FinishAuctionFailure> finishAuctions(List<Long> itemsIds);

    //shop
    List<ShopCategory> getShopCategories();

    //feedback
    int getWaintingFeedbackCounter();
    Observable<WaitingFeedback> getWaitingFeedbacks();
    List<CreatedFeedback> createFeedbacks(List<CreateFeedback> feedbacks);

}
