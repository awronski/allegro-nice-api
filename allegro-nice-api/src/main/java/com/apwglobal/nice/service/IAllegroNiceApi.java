package com.apwglobal.nice.service;

import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.rest.RestApiSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.allegro.webapi.ItemPostBuyDataStruct;
import pl.allegro.webapi.SysStatusType;
import rx.Observable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAllegroNiceApi {

    //rest
    IAllegroNiceApi restLogin(@NotNull String code);
    @Nullable RestApiSession getRestApiSession();
    AllegroNiceApi refreshRestApiSession();

    //login
    IAllegroNiceApi login();
    IAllegroNiceApi loginIfNeeded();
    boolean isLogged();
    AllegroSession getSession();
    long getClientId();

    //info
    Map<Integer, String> getCountries();
    Map<Integer, String> getShippment();

    //status and components
    SysStatusType getStatus();

    //journal + clients data
    Observable<Journal> getJournal(long startingPoint);
    List<ItemPostBuyDataStruct> getClientsDate(long itemId);

    //deals
    Observable<Deal> getDeals(long startingPoint);
    Observable<Payment> getPayments(Observable<Deal> deals);

    //selling
    Observable<Auction> getAuctions();
    Optional<Auction> getAuctionById(long itemId);
    List<AuctionField> getAuctionFields(long itemId);
    ChangedAuctionInfo changeAuctions(long itemId, List<AuctionField> fieldsToModify);
    List<Category> getCategories();
    List<FormField> getSellFormFields(int categoryId);
    NewAuctionPrice checkNewAuction(List<AuctionField> fields);
    CreatedAuction createNewAuction(List<AuctionField> fields);
    ChangedQty changeQty(long itemId, int newQty);
    ChangedPrice changePrice(long itemId, double newPrice);
    List<FinishAuctionFailure> finishAuctions(List<Long> itemsIds);

    //incoming payments
    Observable<IncomingPayment> getIncomingPayments();

}
