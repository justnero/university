package ru.justnero.sevsu.s3.mit.e5;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class Db extends SQLiteOpenHelper {

    private final static String DATABASE = "lab05";

    final static String TABLE = "key_val";

    private final static String COLUMN_ID = "id";
    final static String COLUMN_KEY = "key";
    final static String COLUMN_VALUE = "value";

    Db(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_KEY + " TEXT, "
                + COLUMN_VALUE + " INTEGER " + ");");

        DeleteAll(db);
        LoadData(db);
    }

    private void LoadData(SQLiteDatabase db) {

        String key[] = {"views", "searches", "bumps", "leaks", "heroes"};
        int value[] = {31, 12, 22, 40, 20};

        for (int i = 0; i < key.length; i++) {
            ContentValues element = new ContentValues();

            element.put(COLUMN_VALUE, value[i]);
            element.put(COLUMN_KEY, key[i]);

            db.insert(TABLE, null, element);
        }
    }

    private void DeleteAll(SQLiteDatabase db) {
        db.execSQL("DELETE  FROM " + TABLE + ";");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
