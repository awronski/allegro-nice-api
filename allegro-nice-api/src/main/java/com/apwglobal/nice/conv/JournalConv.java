package com.apwglobal.nice.conv;

import com.apwglobal.nice.domain.Journal;
import pl.allegro.webapi.SiteJournal;

public class JournalConv {

    public static Journal convert(SiteJournal sj) {
        return new Journal.Builder()
                .rowId(sj.getRowId())
                .itemId(sj.getItemId())
                .currentPrice(sj.getCurrentPrice())
                .changeType(sj.getChangeType())
                .changeDate(sj.getChangeDate())
                .build();
    }

}
