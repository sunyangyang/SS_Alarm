package com.clock.sunyangyang.ss.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.clock.sunyangyang.ss.Constants;

/**
 * Created by sunyangyang on 17/9/18.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String CREATE_ALARM="create table " + Constants.ALARM_TABLE +
            "(id integer primary key autoincrement,"+
            Constants.ALARM_HOUR+" integer,"+
            Constants.ALARM_MINUTE+" integer ,"+
            Constants.ALARM_START_TIME+" long,"+
            Constants.ALARM_RING+" text,"+
            Constants.ALARM_RING_TITLE+" text,"+
//            Constants.ALARM_TAG+" text,"+
//            Constants.ALARM_REPEAT_DAY+" text,"+
//            Constants.ALARM_RING_ID+" text,"+
//            Constants.ALARM_ID+" text"+
            ")";

    public DBHelper(Context context) {
        super(context, Constants.SQLDB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
