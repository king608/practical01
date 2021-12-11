package com.example.SQLiteDatabase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
    private String sql1="CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,password TEXT)";
    private String sql2="CREATE TABLE IF NOT EXISTS income(id INTEGER PRIMARY KEY AUTOINCREMENT,uid INTEGER,time text,amount real,category text,fuKuanRen text,beiZhu text)";
    private String sql3="CREATE TABLE IF NOT EXISTS outcome(id INTEGER PRIMARY KEY AUTOINCREMENT,uid INTEGER,time text,amount real,category text,beiZhu text)";
    private String sql4="CREATE TABLE IF NOT EXISTS lavish(id INTEGER PRIMARY KEY AUTOINCREMENT,uid INTEGER,content TEXT)";

    public DBOpenHelper(Context context){
        super(context,"case.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //创建用户表
        db.execSQL(sql1);
        //创建收入表
        db.execSQL(sql2);
        //创建支出表
        db.execSQL(sql3);
        //创建便签表
        db.execSQL(sql4);
        Log.i("表","创建成功");
    }
    //版本适应
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String sql = "Alter table lavish add column title text";
        db.execSQL(sql);
    }


}

