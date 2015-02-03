package com.apwglobal.nice.service;

import pl.allegro.webapi.SysStatusType;

public interface IAllegroNiceApi {

    IAllegroNiceApi login();
    AllegroSession getSession();

    SysStatusType getStatus();

}
