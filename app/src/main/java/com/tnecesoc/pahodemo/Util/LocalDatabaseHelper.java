package com.tnecesoc.pahodemo.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tnecesoc on 2016/9/9.
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public class LocalDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "demo.db";
    private static final int DATABASE_VERSION = 1;

    public LocalDatabaseHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS message_table\n" +
                "(" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "    topic VARCHAR(255) NOT NULL," +
                "    publish_time DATETIME NOT NULL," +
                "    author VARCHAR(255) NOT NULL," +
                "    content TEXT NOT NULL" +
                ");");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
