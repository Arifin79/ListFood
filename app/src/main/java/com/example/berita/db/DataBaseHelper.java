package com.example.berita.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "themealdb";

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_TABLE_MAKANAN = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DataBaseContract.TABLE_MAKANAN,
            DataBaseContract.MakananColumns._ID,
            DataBaseContract.MakananColumns.TITLE,
            DataBaseContract.MakananColumns.DESCRIPTION,
            DataBaseContract.MakananColumns.POSTER

    );

    private static final String SQL_CREATE_TABLE_MINUMAN = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DataBaseContract.TABLE_MINUMAN,
            DataBaseContract.MinumanColumns._ID,
            DataBaseContract.MinumanColumns.TITLE,
            DataBaseContract.MinumanColumns.POSTER
    );
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MAKANAN);
        db.execSQL(SQL_CREATE_TABLE_MINUMAN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.TABLE_MAKANAN);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.TABLE_MINUMAN);
        onCreate(db);
    }
}
