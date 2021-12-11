package com.example.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {
    private DBOpenHelper  mHelper;
    public Dao(Context context){
        mHelper=new DBOpenHelper(context);
    }
    //注册
    public long insertUser(User user) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues valus=new ContentValues();
        valus.put("username",user.getUsername());
        valus.put("password",user.getPassword());
        long id=db.insert("user",null,valus);
        Log.i("提示","插入成功");
        db.close();
        return id;
    }
    //登录时查询
    public int queryUserLogin(User user) {
        SQLiteDatabase db=mHelper.getReadableDatabase();
        String username=user.getUsername();
        String password=user.getPassword();
        Cursor cursor=db.query("user",null,"username=? and password=?",new String[]{
                username,password},null,null,null);
        Log.i("提示","登录查询成功");
        int r=cursor.getCount();
        if(r!=0) {
            cursor.moveToFirst();
            r=cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return r;
    }
    //注册时查询
    public boolean queryUser(User user) {
        SQLiteDatabase db=mHelper.getReadableDatabase();
        String username=user.getUsername();
        //String password=user.getPassword();
        Cursor cursor=db.query("user",null,"username=?",new String[]{
                username},null,null,null);
        Log.i("提示","注册查询成功");
        boolean r=cursor.moveToFirst();
        cursor.close();
        db.close();
        return r;
    }
    //系统设置修改密码,根据用户名
    public int updateUser(String username,String password){
        SQLiteDatabase db=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("password",password);
        int i=db.update("user",values,"username=?",new String[]{username});
        db.close();
        return i;
    }



}
