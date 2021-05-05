package com.example.ben_e.mediam.database;

import android.provider.BaseColumns;

/**
 * Created by ben-e on 8-10-17.
 */

public final class LogbookContract {
    private LogbookContract() {}

    public static class LogbookEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "logbook";
        public static final String COLUMN_NAME_MEDIA = "media";
        public static final String COLUMN_NAME_DATE = "datum";
        public static final String COLUMN_NAME_STARTTIJD = "starttijd";
        public static final String COLUMN_NAME_EINDTIJD = "eindtijd";
        public static final String COLUMN_NAME_MENING = "mening";
    }
}
