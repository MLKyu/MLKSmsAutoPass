package com.mlk.smsautopass.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mingeek on 2016-12-26.
 */

public class DBPhoneNumberAdapter {
    private static final String DATABASE_NAME = "SMSAutoPass.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME = "SMSAutoPass";
    private static final String TAG_NAME = "DBAdapter";
    private Context context;
    private SQLiteDatabase db;
    private DBSQLiteOpenHelper helper;

    public DBPhoneNumberAdapter(Context paramContext) {
        this.context = paramContext;
    }

    public void close() {
        this.helper.close();
    }

    public void deleteAll() {
        this.db.delete("SMSAutoPass", null, null);
    }

    public void deletePhoneNumber(int paramInt) {
        this.db.execSQL("delete from SMSAutoPass where id=?", new Object[]{new Integer(paramInt)});
    }

    public Cursor fetchAll() {
        return this.db.query("SMSAutoPass", new String[]{"id", "phonenumber", "profile_name", "enabled", "rules", "recent_time"}, null, null, null, null, "id asc");
    }

    public Cursor fetchPhoneNumber(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = this.db;
        String str = "id=" + paramInt;
        return localSQLiteDatabase.query("SMSAutoPass", new String[]{"id", "phonenumber", "profile_name", "enabled", "rules", "recent_time"}, str, null, null, null, "id asc");
    }

    public long insertPhoneNumber(String paramString1, String paramString2) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("phonenumber", paramString1);
        localContentValues.put("profile_name", paramString2);
        localContentValues.put("enabled", "true");
        return this.db.insert("SMSAutoPass", null, localContentValues);
    }

    public void open() {
        this.helper = new DBSQLiteOpenHelper();
        this.db = this.helper.getWritableDatabase();
    }

    public void updateEnabled(int paramInt, String paramString) {
        this.db.execSQL("update SMSAutoPass set enabled=? where id=?;", new Object[]{paramString, String.valueOf(paramInt)});
    }

    public void updatePhoneNumber(int paramInt, String paramString1, String paramString2) {
        this.db.execSQL("update SMSAutoPass set phonenumber=?, profile_name=? where id=?;", new Object[]{paramString1, paramString2, String.valueOf(paramInt)});
    }

    public void updateRecentTime(int paramInt, String paramString) {
        this.db.execSQL("update SMSAutoPass set recent_time=? where id=?;", new Object[]{paramString, String.valueOf(paramInt)});
    }

    public void updateRules(int paramInt, String paramString) {
        this.db.execSQL("update SMSAutoPass set rules=? where id=?;", new Object[]{paramString, String.valueOf(paramInt)});
    }

    class DBSQLiteOpenHelper extends SQLiteOpenHelper {
        public DBSQLiteOpenHelper() {
            super(context, "SMSAutoPass.db", null, 3);
        }

        public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append("CREATE TABLE SMSAutoPass(  ");
            localStringBuffer.append("id INTEGER PRIMARY KEY AUTOINCREMENT,  ");
            localStringBuffer.append("phonenumber TEXT NOT NULL, profile_name TEXT NOT NULL, enabled TEXT, rules TEXT, recent_time TEXT);   ");
            paramSQLiteDatabase.execSQL(localStringBuffer.toString());
        }

        public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
            paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS  SMSAutoPass;  ");
            onCreate(paramSQLiteDatabase);
        }
    }
}