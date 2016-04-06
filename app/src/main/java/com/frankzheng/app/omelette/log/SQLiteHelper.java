package com.frankzheng.app.omelette.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/4/5.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHelper.class.getSimpleName();

    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;

    private static final String LOG_TABLE_NAME = "LOG";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_LEVEL = "LEVEL";
    private static final String COLUMN_TAG = "TAG";
    private static final String COLUMN_LOG = "LOG";


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        String sql = String.format("create table %s ( %s, %s, %s, %s, %s )",
                LOG_TABLE_NAME,
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
                COLUMN_DATE + " BIGINT",
                COLUMN_LEVEL + " INTEGER",
                COLUMN_TAG + " VARCHAR(255)",
                COLUMN_LOG + " VARCHAR");
        Log.d(TAG, sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //migrate data if necessary
        Log.i(TAG, "onUpgrade " + oldVersion + ", " + newVersion);
        onCreate(db);
    }

    public synchronized long insertRecord(LogRecord record) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_DATE, record.getDate().getTime());
            cv.put(COLUMN_LEVEL, record.getLevel());
            cv.put(COLUMN_TAG, record.getTag());
            cv.put(COLUMN_LOG, record.getLog());
            long ret = db.insert(LOG_TABLE_NAME, null, cv);
            db.close();
            return ret;
        }
        return -1;
    }

    public synchronized List<LogRecord> getAllRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return null;
        }

        Cursor cursor = db.query(LOG_TABLE_NAME, null, null, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        List<LogRecord> records = new ArrayList<>(cursor.getCount());
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            LogRecord record = new LogRecord();
            Date date = new Date(cursor.getLong(1));
            record.setDate(date);
            record.setLevel(cursor.getInt(2));
            record.setTag(cursor.getString(3));
            record.setLog(cursor.getString(4));
            records.add(record);
        }
        cursor.close();
        db.close();
        return records;
    }


}
