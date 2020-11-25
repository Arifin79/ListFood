package com.example.berita.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.berita.model.minuman.DrinksItem;

import java.sql.SQLException;
import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.berita.db.DataBaseContract.MakananColumns.TITLE;
import static com.example.berita.db.DataBaseContract.MinumanColumns.POSTER;
import static com.example.berita.db.DataBaseContract.TABLE_MINUMAN;

public class MinumanHelper {
    private static final String DATABASE_TABLE = TABLE_MINUMAN;
    private static DataBaseHelper dataBaseHelper;
    private static MinumanHelper INSTANCE;
    private static SQLiteDatabase database;

    private MinumanHelper(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public static MinumanHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null) {
                    INSTANCE = new MinumanHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<DrinksItem> getAllMinuman() {
        ArrayList<DrinksItem> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        DrinksItem note;
        if (cursor.getCount() > 0) {
            do {
                note = new DrinksItem();
                note.setIdDrink(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setStrDrink(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                note.setStrDrinkThumb(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                arrayList.add(note);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public DrinksItem getMinumanById(int id) {
        Cursor cursor = database.query(
                DATABASE_TABLE,
                new String[]{_ID, TITLE, POSTER},
                _ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        DrinksItem drinksItem = new DrinksItem();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            drinksItem.setIdDrink(cursor.getInt(cursor.getColumnIndex(_ID)));
            drinksItem.setStrDrink(cursor.getString(cursor.getColumnIndex(TITLE)));
            drinksItem.setStrDrinkThumb(cursor.getString(cursor.getColumnIndex(POSTER)));

            cursor.close();
            return drinksItem;
        }

        return null;

    }

    public long insertMinuman(DrinksItem drink) {
        ContentValues args = new ContentValues();
        args.put(_ID, drink.getIdDrink());
        args.put(TITLE, drink.getStrDrink());
        args.put(POSTER, drink.getStrDrinkThumb());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMinuman(int id) {
        return database.delete(DATABASE_TABLE, _ID + "=?", new String[]{String.valueOf(id)});
    }


}
