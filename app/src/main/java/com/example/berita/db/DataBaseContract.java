package com.example.berita.db;

import android.provider.BaseColumns;

public class DataBaseContract {
    static String TABLE_MAKANAN = "tb_makanan";
    static String TABLE_MINUMAN = "tb_minuman";

    static final class MakananColumns implements BaseColumns {
        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String POSTER = "poster";
    }

    static final class MinumanColumns implements BaseColumns {
        static String TITLE = "title";
        static String POSTER = "poster";
    }
}
