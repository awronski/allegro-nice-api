package com.apwglobal.nice.service;

import com.apwglobal.nice.message.AllegroMessage;
import pl.allegro.webapi.SysStatusType;

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


}
