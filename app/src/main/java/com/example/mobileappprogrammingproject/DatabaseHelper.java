package com.example.mobileappprogrammingproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스 테이블 생성 SQL문 작성
        String createTableQuery = "CREATE TABLE items (id INTEGER PRIMARY KEY, name TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 버전 업그레이드 시 동작할 코드 작성
        // 필요한 경우 테이블 재생성 등의 작업을 수행할 수 있습니다.
    }

    public ArrayList<TodayItem> getItemsFromDatabase() {
        ArrayList<TodayItem> items = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM items", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));

                // TodayItem 객체 생성 후 데이터 추가
                TodayItem item = new TodayItem(name);
                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return items;
    }

    public long insertData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        long result = db.insert("items", null, values);
        db.close();
        return result;
    }

    public int deleteData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "name=?";
        String[] whereArgs = {name};
        int result = db.delete("items", whereClause, whereArgs);
        db.close();
        return result;
    }

    public static ArrayList<TodayItem> loadItemsFromDatabase(DatabaseHelper dbHelper) {
        ArrayList<TodayItem> itemList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"name"};
        Cursor cursor = db.query("items", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            itemList.add(new TodayItem(name));
        }

        cursor.close();
        return itemList;
    }
}
