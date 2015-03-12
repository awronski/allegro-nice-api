package com.apwglobal.nice.util;

import java.util.Date;

public class UnixDate {

    public static Date toDate(long unixTimestamp) {
        return new Date(unixTimestamp * 1000);
    }

    public static long toUnixTimestamp(Date date) {
        return date.getTime() / 1000;
    }

}
