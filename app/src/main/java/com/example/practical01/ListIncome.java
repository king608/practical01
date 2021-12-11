package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Mysystem.SysApplication;
import com.example.SQLiteDatabase.IncomeClass;
import com.example.SQLiteDatabase.IncomeDao;

import java.util.ArrayList;

public class ListIncome extends AppCompatActivity {
    private String[] category=new String[500];
    private String[] amount=new String[500];
    private String[] time=new String[500];
    private int cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_income);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);

        //返回键,线程的方式
        ImageView iv_back14=findViewById(R.id.iv_back14);
        iv_back14.setOnClickListener(new View.OnClickListener() {
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
        IncomeDao inc=new IncomeDao(ListIncome.this);
        ArrayList<IncomeClass> list=new ArrayList<IncomeClass>();
        //从sharedpreferences中获取uid
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        int uid=sp.getInt("id",0);
        list=inc.queryIncome(uid);
        if(list!=null){
            cnt=list.size();
            for(int i = 0 ; i < cnt ; i++) {
                category[i]=list.get(i).getCategory();
                amount[i]=list.get(i).getAmount();
                time[i]=list.get(i).getTime();
            }
        }
        //LsitView
        ListView mListview=findViewById(R.id.listview1);
        MyBaseAdapter mAdapter=new MyBaseAdapter();//创建Adapter实例
        mListview.setAdapter(mAdapter);//设置数据适配器



    }
    class MyBaseAdapter extends BaseAdapter{

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
            View view1=View.inflate(ListIncome.this,R.layout.list_item,null);
            TextView list_item1=view1.findViewById(R.id.list_item1);
            TextView list_item2=view1.findViewById(R.id.list_item2);
            TextView list_item3=view1.findViewById(R.id.list_item3);
            list_item1.setText(category[i]);
            list_item2.setText("￥"+amount[i]);
            list_item3.setText(time[i]);
            return view1;
        }
    }
}