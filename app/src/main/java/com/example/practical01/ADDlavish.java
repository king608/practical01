package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Mysystem.SysApplication;
import com.example.lavish.LavishClass;
import com.example.lavish.LavishDao;

public class ADDlavish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlavish);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);

        //返回键,线程的方式
        ImageView iv_back20=findViewById(R.id.iv_back20);
        iv_back20.setOnClickListener(new View.OnClickListener() {
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

        //提交保存,插入数据
        Button add_server=findViewById(R.id.add_server);
        add_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取数据
                EditText et_title=findViewById(R.id.title);
                EditText et_content=findViewById(R.id.content);
                String title=et_title.getText().toString().trim();
                String content=et_content.getText().toString().trim();
                //检查输入是否符合规则
                if((!title.isEmpty())&&(!content.isEmpty())){
                    //从sharedpreferences中获取uid
                    SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                    int uid=sp.getInt("id",0);
                    if(uid==0){
                        Toast.makeText(ADDlavish.this,"保存失败！",Toast.LENGTH_SHORT).show();
                    }else{
                        LavishClass lavish=new LavishClass(uid,content,title);
                        LavishDao dao=new LavishDao(ADDlavish.this);
                        long i=dao.insertLavish(lavish);
                        if(i>0){
                            Toast.makeText(ADDlavish.this,"保存成功！",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ADDlavish.this,"保存失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    //检查输入不符合规则
                    Toast.makeText(ADDlavish.this,"请保证标题与内容不为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}