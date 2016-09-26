package com.tnecesoc.pahodemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tnecesoc.pahodemo.Bean.MessageBean;
import com.tnecesoc.pahodemo.Util.LocalDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tnecesoc on 2016/9/8.
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public class LocalRepository {

    private SQLiteDatabase localDB;
    private LocalDatabaseHelper helper;

    public LocalRepository(Context context) {

        helper = new LocalDatabaseHelper(context);
        localDB = helper.getWritableDatabase();

    }

    public void insertNewMessage(MessageBean message) {

        localDB.beginTransaction();

        try {
            localDB.execSQL("INSERT INTO message_table (topic, publish_time, author, content) VALUES (?, ?, ?, ?)", new String[]{message.getTopic(), message.getPublishTime(), message.getAuthor(), message.getContent()});
            localDB.setTransactionSuccessful();
        } finally {
            localDB.endTransaction();
        }

    }

    public List<MessageBean> findMessages() {
        List<MessageBean> ans = new ArrayList<>();

        Cursor it = localDB.rawQuery("SELECT * FROM message_table ORDER BY publish_time", null);

        while (it.moveToNext()) {
            ans.add(new MessageBean(
                    it.getString(it.getColumnIndex("topic")),
                    it.getString(it.getColumnIndex("publish_time")),
                    it.getString(it.getColumnIndex("author")),
                    it.getString(it.getColumnIndex("content"))
            ));
        }

        it.close();

        return ans;
    }

    public void close() {

        localDB.execSQL("DROP TABLE IF EXISTS message_table");

    }


}
