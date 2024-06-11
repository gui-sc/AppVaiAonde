package com.example.vaiaonde.database.dao;

import android.database.sqlite.SQLiteDatabase;

import com.example.vaiaonde.database.DBOpenHelper;

public abstract class AbstrataDao {
    protected SQLiteDatabase db;
    protected DBOpenHelper db_helper;

    protected final void Open() {
        db = db_helper.getWritableDatabase();
    }

    protected  final void Close() {
        db_helper.close();
    }
}
