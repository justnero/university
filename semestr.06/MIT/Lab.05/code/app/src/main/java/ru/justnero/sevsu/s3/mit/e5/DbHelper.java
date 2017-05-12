package ru.justnero.sevsu.s3.mit.e5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

class DbHelper {

    static void Add(Context context, String key, int value) {
        Db db = new Db(context);
        SQLiteDatabase table = db.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Db.COLUMN_KEY, key);
        cv.put(Db.COLUMN_VALUE, value);
        Log.d("Lab.05", "ADD " + key + " => " + value);
        table.insert(Db.TABLE, null, cv);
    }

    static void UpdateLast(Context context, String key, int value) {
        Db db = new Db(context);
        SQLiteDatabase table = db.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Db.COLUMN_KEY, key);
        cv.put(Db.COLUMN_VALUE, value);
        table.update(Db.TABLE, cv, "ID = (SELECT MAX(t1.ID) FROM " + Db.TABLE + " as t1)", null);
    }

    static ArrayList<Option> getTableData(Context context) {
        ArrayList<Option> map = new ArrayList<>();

        Db db = new Db(context);
        SQLiteDatabase table = db.getWritableDatabase();
        Cursor cursor = table.query(Db.TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            String key;
            int value;
            do {
                key = cursor.getString(cursor.getColumnIndex(Db.COLUMN_KEY));
                value = cursor.getInt(cursor.getColumnIndex(Db.COLUMN_VALUE));
                Log.d("Lab.05", key + " => " + value);
                map.add(new Option(key, value));
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return map;
    }
}
