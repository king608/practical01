package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Mysystem.SysApplication;
import com.example.SQLiteDatabase.Dao;
import com.example.SQLiteDatabase.User;

public class Register extends AppCompatActivity implements View.OnClickListener  {
    //找到控件
    private EditText et_rgsName;
    private EditText et_rgsPassword;
    private Button btn_rgs;
    private ImageView iv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);
        initView();
    }
    public void initView(){
        btn_rgs=findViewById(R.id.btn_rgs);
        iv1=findViewById(R.id.iv_back1);
        iv1.setOnClickListener(this);
        btn_rgs.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rgs:
                register();
                break;
            case R.id.iv_back1:
                Back();
                break;
        }
    }
    //注册功能
    public void register() {
        et_rgsName=findViewById(R.id.et_rgsName);
        et_rgsPassword=findViewById(R.id.et_rgsPassword);
        EditText et_rgsPassword1=findViewById(R.id.et_rgsPassword1);
        String name=et_rgsName.getText().toString().trim();
        String pwd=et_rgsPassword.getText().toString().trim();
        String pwd1=et_rgsPassword1.getText().toString().trim();
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(pwd1)){
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
        }else{
            if(!pwd.equals(pwd1)){
                Toast.makeText(this,"密码与确认密码不一致！",Toast.LENGTH_SHORT).show();
            }else {
                User user = new User(name, pwd);
                Dao dao = new Dao(this);
                if (dao.queryUser(user) != false) {
                    Toast.makeText(this, "用户已存在，请直接登陆", Toast.LENGTH_SHORT).show();
                } else {
                    long id = dao.insertUser(user);
                    if (id != -1)
                        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                }
                //结束注册，返回登录
                finish();
            }

        }

    }
    //
    public void Back(){
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
}