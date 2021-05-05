package com.example.ben_e.mediam.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

/**
 * Created by ben-e on 8-10-17.
 */

public class LogbookDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "MediaLogboek.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LogbookContract.LogbookEntry.TABLE_NAME + " (" +
                    LogbookContract.LogbookEntry._ID + " INTEGER PRIMARY KEY, " +
                    LogbookContract.LogbookEntry.COLUMN_NAME_MEDIA + " TEXT, " +
                    LogbookContract.LogbookEntry.COLUMN_NAME_DATE + " DATETIME, " +
                    LogbookContract.LogbookEntry.COLUMN_NAME_STARTTIJD + " TEXT, " +
                    LogbookContract.LogbookEntry.COLUMN_NAME_EINDTIJD + " TEXT, " +
                    LogbookContract.LogbookEntry.COLUMN_NAME_MENING + " TEXT" +
                    ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LogbookContract.LogbookEntry.TABLE_NAME;

    public LogbookDbHelper(Context context)
    {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + "/Mediam/" + File.separator
                + DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
