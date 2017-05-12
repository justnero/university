package ru.justnero.sevsu.s3.mit.e6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DataBase extends SQLiteOpenHelper {
    final static String DATABASE = "lab06";
    final static String TABLE = "music_history";
    final static String COLUMN_ID = "id";
    final static String COLUMN_ARTIST = "author";
    final static String COLUMN_SONG = "song";
    final static String COLUMN_TIME = "time";

    DataBase(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ARTIST + " TEXT, " +
                COLUMN_SONG + " TEXT, " +
                COLUMN_TIME + " TEXT " + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
