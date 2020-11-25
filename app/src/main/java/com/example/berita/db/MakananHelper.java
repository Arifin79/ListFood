package com.example.berita.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.berita.model.makanan.MealsItem;

import java.sql.SQLException;
import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static android.provider.MediaStore.MediaColumns.TITLE;
import static com.example.berita.db.DataBaseContract.MakananColumns.DESCRIPTION;
import static com.example.berita.db.DataBaseContract.MakananColumns.POSTER;
import static com.example.berita.db.DataBaseContract.TABLE_MAKANAN;

public class MakananHelper {
    private static final String DATABASE_TABLE = TABLE_MAKANAN;
    private static DataBaseHelper dataBaseHelper;
    private static MakananHelper INSTANCE;
    private static SQLiteDatabase database;

    private MakananHelper(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public static MakananHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null) {
                    INSTANCE = new MakananHelper(context);
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

    public ArrayList<MealsItem> getAllMakanan() {
        ArrayList<MealsItem> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        MealsItem note;
        if (cursor.getCount() > 0) {
            do {
                note = new MealsItem();
                note.setIdCategory(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setStrCategory(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                note.setStrCategoryDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                note.setStrCategoryThumb(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                arrayList.add(note);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public MealsItem getMakananById(int id) {
        Cursor cursor = database.query(
                DATABASE_TABLE,
                new String[]{_ID, TITLE, POSTER, DESCRIPTION},
                _ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        MealsItem mealsItem = new MealsItem();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mealsItem.setIdCategory(cursor.getInt(cursor.getColumnIndex(_ID)));
            mealsItem.setStrCategory(cursor.getString(cursor.getColumnIndex(TITLE)));
            mealsItem.setStrCategoryDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            mealsItem.setStrCategoryThumb(cursor.getString(cursor.getColumnIndex(POSTER)));

            cursor.close();
            return mealsItem;
        }

        return null;

    }

    public long insertMakanan(MealsItem movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getIdCategory());
        args.put(TITLE, movie.getStrCategory());
        args.put(DESCRIPTION, movie.getStrCategoryDescription());
        args.put(POSTER, movie.getStrCategoryThumb());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMakanan(int id) {
        return database.delete(DATABASE_TABLE, _ID + "=?", new String[]{String.valueOf(id)});
    }


}
