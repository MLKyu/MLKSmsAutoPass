package com.mlk.smsautopass.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mingeek on 2016-12-26.
 */

public class DBSendLogAdapter {
    private static final String DATABASE_NAME = "SMSSendLog.db";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_NAME = "SMSSendLog";
    private static final String TAG_NAME = "DBAdapter";
    private Context context;
    private SQLiteDatabase db;
    private DBSQLiteOpenHelper helper;

    public DBSendLogAdapter(Context paramContext) {
        this.context = paramContext;
    }

    public void close() {
        this.helper.close();
    }

    public void deleteAll() {
        this.db.delete("SMSSendLog", null, null);
    }

    public void deleteLog(int paramInt) {
        this.db.execSQL("delete from SMSSendLog where id=?", new Object[]{new Integer(paramInt)});
    }

    public Cursor fetchAllLog() {
        return this.db.query("SMSSendLog", new String[]{"id", "this_rule", "from_phonenumber", "to_phonenumber", "send_text", "send_time", "success"}, null, null, null, null, "id asc");
    }

    public Cursor fetchLog(String paramString) {
        return this.db.rawQuery("select id, this_rule, from_phonenumber, to_phonenumber, send_text, send_time, success from SMSSendLog where to_phonenumber=?; ", new String[]{paramString});
    }

    public long insertSendLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("this_rule", paramString1);
        localContentValues.put("from_phonenumber", paramString2);
        localContentValues.put("to_phonenumber", paramString3);
        localContentValues.put("send_text", paramString4);
        localContentValues.put("send_time", paramString5);
        localContentValues.put("success", paramString6);
        return this.db.insert("SMSSendLog", null, localContentValues);
    }

    public void open() {
        this.helper = new DBSQLiteOpenHelper();
        this.db = this.helper.getWritableDatabase();
    }

    class DBSQLiteOpenHelper extends SQLiteOpenHelper {
        public DBSQLiteOpenHelper() {
            super(context, "SMSSendLog.db", null, 4);
        }

        public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append("CREATE TABLE SMSSendLog(  ");
            localStringBuffer.append("id INTEGER PRIMARY KEY AUTOINCREMENT,  ");
            localStringBuffer.append("this_rule TEXT, from_phonenumber TEXT, to_phonenumber TEXT, send_text TEXT, send_time TEXT, success TEXT);   ");
            paramSQLiteDatabase.execSQL(localStringBuffer.toString());
        }

        public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
            paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS  SMSSendLog;  ");
            onCreate(paramSQLiteDatabase);
        }
    }
}