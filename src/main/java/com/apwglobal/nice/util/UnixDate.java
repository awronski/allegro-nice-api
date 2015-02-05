package com.apwglobal.nice.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class UnixDate {

    public static LocalDateTime toDate(long unixTimestamp) {
        return LocalDateTime.ofInstant(new Date(unixTimestamp * 1000).toInstant(), ZoneId.systemDefault());
    }

    public static long toUnixTimestamp(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000;
    }

}
