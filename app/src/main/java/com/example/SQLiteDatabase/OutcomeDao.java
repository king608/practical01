package com.example.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.practical01.Outcome;

import java.util.ArrayList;

public class OutcomeDao {
    private DBOpenHelper  mHelper;
    public OutcomeDao(Context context){
        mHelper=new DBOpenHelper(context);
    }

    //添加数据
    public long insertOutcome(OutcomeClass outcome) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues valus=new ContentValues();
        valus.put("uid",outcome.getUid());
        valus.put("time",outcome.getTime());
        valus.put("amount",outcome.getAmount());
        valus.put("category",outcome.getCategory());
        valus.put("beiZhu",outcome.getBeiZhu());
        long id=db.insert("outcome",null,valus);
        Log.i("提示","插入成功"+id);
        db.close();
        return id;
    }

    //根据uid查询数据,返回Arraylist集合
    public ArrayList<OutcomeClass> queryOutcome(int uid){
        SQLiteDatabase db=mHelper.getReadableDatabase();
        ArrayList<OutcomeClass> list=new ArrayList<OutcomeClass>();
        Cursor cursor=db.rawQuery("select *from outcome where uid=? order by time desc",new String[]{uid+""});
        //总条数
        int cnt=cursor.getCount();
        Log.i("记录总条数",cnt+"");
        if(cnt==0){
            list=null;
        }else{
            //遍历数据
            while (cursor.moveToNext()) {
                int id=cursor.getInt(0);
                String time=cursor.getString(2);
                String amount= cursor.getString(3);
                String category= cursor.getString(4);
                String beiZhu= cursor.getString(5);
                OutcomeClass out=new OutcomeClass(id,uid,time,amount,category,beiZhu);
                list.add(out);
            }
        }
        cursor.close();
        db.close();
        return list;
    }
    //删除所有的支出记录
    public int deleteOutcome(int uid){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //db.execSQL("delete from outcome where uid=?",new String[]{uid+""});
        int h=db.delete("outcome","uid=?",new String[]{uid+""});
        db.close();
        return h;
    }

}
