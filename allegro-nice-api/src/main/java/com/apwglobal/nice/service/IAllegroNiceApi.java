package com.apwglobal.nice.service;

import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.Journal;
import com.apwglobal.nice.message.AllegroMessage;
import pl.allegro.webapi.ItemPostBuyDataStruct;
import pl.allegro.webapi.SysStatusType;
import rx.Observable;

import java.time.LocalDateTime;
import java.util.List;

public interface IAllegroNiceApi {

    //login
    IAllegroNiceApi login();
    AllegroSession getSession();

    //status and components
    SysStatusType getStatus();

    //messages
    List<AllegroMessage> getAllMessages(LocalDateTime from);

    //journal + clients data
    Observable<Journal> getJournal(long startingPoint);
    List<ItemPostBuyDataStruct> getClientsDate(long itemId);

    //deals
    Observable<Deal> getDeals(long startingPoint);
    void fillBuyersForms(List<Deal> deals);

    //selling
    Observable<Auction> getAuctions();

}
