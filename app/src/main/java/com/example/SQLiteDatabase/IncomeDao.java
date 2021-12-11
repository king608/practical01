package com.example.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class IncomeDao {
    private DBOpenHelper  mHelper;
    public IncomeDao(Context context){
        mHelper=new DBOpenHelper(context);
    }
    //添加收入记录
    public long insertIncome(IncomeClass income) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues valus=new ContentValues();
        valus.put("uid",income.getUid());
        valus.put("time",income.getTime());
        valus.put("amount",income.getAmount());
        valus.put("category",income.getCategory());
        valus.put("fuKuanRen",income.getFuKuanRen());
        valus.put("beiZhu",income.getBeiZhu());
        long id=db.insert("income",null,valus);
        Log.i("提示","插入成功"+id);
        db.close();
        return id;
    }

    //查询收入表并且返回一个List集合
    public ArrayList<IncomeClass> queryIncome(int uid){
        ArrayList<IncomeClass> list=new ArrayList<IncomeClass>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from income where uid=? order by time desc",new String[]{uid+""});
        //总条数
        int cnt=cursor.getCount();
        Log.i("cnt",cnt+"");
        if(cnt==0){
            list=null;
        }else{
            //遍历数据
            while (cursor.moveToNext()) {
                int id=cursor.getInt(0);
                //int uuid=cursor.getInt(1);
                String time=cursor.getString(2);
                String amount=cursor.getString(3);
                String category=cursor.getString(4);
                String fukuanRen= cursor.getString(5);
                String beiZhu= cursor.getString(6);
                IncomeClass in=new IncomeClass(id,uid,time,amount,category,fukuanRen,beiZhu);
                list.add(in);
            }

        }

        cursor.close();
        db.close();
        return list;
    }

    //删除所有的收入记录
    public int deleteIncome(int uid){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //db.execSQL("delete from income where uid=?",new String[]{uid+""});
        int h=db.delete("income","uid=?",new String[]{uid+""});
        db.close();
        return h;
    }
}
