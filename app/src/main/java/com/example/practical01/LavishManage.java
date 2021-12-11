package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Mysystem.SysApplication;
import com.example.lavish.LavishClass;
import com.example.lavish.LavishDao;

import java.util.ArrayList;

public class LavishManage extends AppCompatActivity {
    private int[] id=new int[500];
    private String[] title=new String[500];
    private String[] content=new String[500];
    private int cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lavish_manage);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);

        //返回键,线程的方式
        ImageView iv_back16=findViewById(R.id.iv_back16);
        iv_back16.setOnClickListener(new View.OnClickListener() {
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
        //添加键
        ImageView add=findViewById(R.id.add_lavish);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LavishManage.this,ADDlavish.class);
                startActivity(intent);
            }
        });

        //查询数据
        ArrayList<LavishClass> list=new ArrayList<LavishClass>();
        LavishDao dao=new LavishDao(LavishManage.this);
        //从sharedpreferences中获取uid
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        int uid=sp.getInt("id",0);
        list=dao.queryLavish(uid);
        Log.i("uid",uid+"");
        if(list!=null){
            cnt=list.size();
            for(int i = 0 ; i < cnt ; i++) {
                id[i]=list.get(i).getId();
                title[i]=list.get(i).getTitle();
                content[i]=list.get(i).getContent();
                Log.i("title",title[i]);
            }
        }
        ListView mListview=findViewById(R.id.listview5);
        MyBaseAdapter3 mAdapter=new MyBaseAdapter3();//创建Adapter实例
        mListview.setAdapter(mAdapter);//设置数据适配器
    }

    class MyBaseAdapter3 extends BaseAdapter {

        @Override
        public int getCount() {
            return cnt;
        }

        @Override
        public Object getItem(int i) {
            return title[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v=View.inflate(LavishManage.this,R.layout.list_item_lavish,null);
            TextView list3=v.findViewById(R.id.list_item43);
            TextView list1=v.findViewById(R.id.list_item44);
            TextView list2=v.findViewById(R.id.list_item45);
            list3.setText(id[i]+"");
            list1.setText(title[i]);
            list2.setText(content[i]);
            return v;
        }
    }
}