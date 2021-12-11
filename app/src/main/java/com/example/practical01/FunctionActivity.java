package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.Mysystem.SysApplication;

public class FunctionActivity extends AppCompatActivity implements View.OnClickListener {
    //对应功能页面的8个功能
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private ImageView iv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);
        //初始化控件
        iv1=findViewById(R.id.imageView1);
        iv2=findViewById(R.id.imageView2);
        iv3=findViewById(R.id.imageView3);
        iv4=findViewById(R.id.imageView4);
        iv5=findViewById(R.id.imageView5);
        iv6=findViewById(R.id.imageView6);
        iv7=findViewById(R.id.imageView7);
        iv8=findViewById(R.id.imageView8);
        iv1.setOnClickListener(this);//新增收入
        iv2.setOnClickListener(this);//新增支出
        iv3.setOnClickListener(this);//我的收入
        iv4.setOnClickListener(this);//我的支出
        iv5.setOnClickListener(this);//数据管理
        iv6.setOnClickListener(this);//收支便签
        iv7.setOnClickListener(this);//系统设置
        iv8.setOnClickListener(this);//退出
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView1:
                //跳转到新增支出页面
                Intent intent1=new Intent(FunctionActivity.this,Outcome.class);
                startActivity(intent1);
                break;
            case R.id.imageView2:
                //跳转到新增收入页面
                Intent intent2=new Intent(FunctionActivity.this,Income.class);
                startActivity(intent2);
                 break;
            case R.id.imageView3:
                //跳转到我的支出列表
                Intent intent3=new Intent(FunctionActivity.this,ListOutcome.class);
                startActivity(intent3);
                break;
            case R.id.imageView4:
                //跳转到我的收入页面
                Intent intent4=new Intent(FunctionActivity.this,ListIncome.class);
                startActivity(intent4);
                break;
            case R.id.imageView5:
                //跳转到数据管理
                Intent intent5=new Intent(FunctionActivity.this,DataManager.class);
                startActivity(intent5);
                break;
            case R.id.imageView6:
                //跳转到便签管理
                Intent intent6=new Intent(FunctionActivity.this,LavishManage.class);
                startActivity(intent6);
                break;
            case R.id.imageView7:
                //跳转到系统设置页面
                Intent intent7=new Intent(FunctionActivity.this,SystemSettings.class);
                startActivity(intent7);
                break;
            case R.id.imageView8:
                //关闭整个程序//退出功能
                SysApplication.getInstance().exit();
                break;
        }

    }


}