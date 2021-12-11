package com.example.lavish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.SQLiteDatabase.DBOpenHelper;
import com.example.SQLiteDatabase.User;

import java.util.ArrayList;

public class LavishDao {
    private DBOpenHelper mHelper;
    public LavishDao(Context context){
        mHelper=new DBOpenHelper(context);
    }
    //添加便签
    public long insertLavish(LavishClass lavish) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues valus=new ContentValues();
        valus.put("uid",lavish.getUid());
        valus.put("content",lavish.getContent());
        valus.put("title",lavish.getTitle());
        long id=db.insert("lavish",null,valus);
        Log.i("便签","添加成功");
        db.close();
        return id;
    }
    //根据uid查询数据,并且返回一个List集合
    public ArrayList<LavishClass> queryLavish(int uid){
        ArrayList<LavishClass> list=new ArrayList<LavishClass>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from lavish where uid=? order by id asc",new String[]{uid+""});
        //总条数
        int cnt=cursor.getCount();
        Log.i("便签条数",cnt+"");
        if(cnt==0){
            list=null;
        }else {
            //遍历数据
            while (cursor.moveToNext()) {
                int id=cursor.getInt(0);
                String content=cursor.getString(2);
                String title=cursor.getString(3);
                LavishClass y=new LavishClass(id,uid,content,title);
                list.add(y);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

}
