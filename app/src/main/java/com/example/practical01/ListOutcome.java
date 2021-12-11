package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Mysystem.SysApplication;
import com.example.SQLiteDatabase.OutcomeClass;
import com.example.SQLiteDatabase.OutcomeDao;

import java.util.ArrayList;

public class ListOutcome extends AppCompatActivity {
    private String[] category=new String[500];
    private String[] amount=new String[500];
    private String[] time=new String[500];
    private int cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_outcome);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);

        //返回键,线程的方式
        ImageView iv_back13=findViewById(R.id.iv_back13);
        iv_back13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {

                        }
                    }
                }.start();
            }

        });

        //查询数据
        OutcomeDao o=new OutcomeDao(ListOutcome.this);
        ArrayList<OutcomeClass> list=new ArrayList<OutcomeClass>();
        //从sharedpreferences中获取uid
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        int uid=sp.getInt("id",0);
        list=o.queryOutcome(uid);
        if(list!=null){
            cnt=list.size();
            for(int i = 0 ; i < cnt ; i++) {
                category[i]=list.get(i).getCategory();
                amount[i]=list.get(i).getAmount();
                time[i]=list.get(i).getTime();
            }

        }
        //LsitView
        ListView mListview2=findViewById(R.id.listview2);
        MyBaseAdapter2 mAdapter2=new MyBaseAdapter2();//创建Adapter实例
        mListview2.setAdapter(mAdapter2);//设置数据适配器
    }

    class MyBaseAdapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            //item总数
            return cnt;
        }

        @Override
        public Object getItem(int i) {
            //item对象
            return category[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2=View.inflate(ListOutcome.this,R.layout.list_itm_out,null);
            TextView list_item11=view2.findViewById(R.id.list_item11);
            TextView list_item22=view2.findViewById(R.id.list_item22);
            TextView list_item33=view2.findViewById(R.id.list_item33);
            list_item11.setText(category[i]);
            list_item22.setText("￥"+amount[i]);
            list_item33.setText(time[i]);
            return view2;
        }
    }
}