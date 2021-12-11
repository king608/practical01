package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Mysystem.SysApplication;
import com.example.SQLiteDatabase.Dao;
import com.example.SQLiteDatabase.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private Button mBtnLogin;
    private Button mRegister;
    private EditText eTu;
    private EditText eTpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);
        //找到控件
        mBtnLogin=findViewById(R.id.btn_login);
        mRegister=findViewById(R.id.btn_register);
        eTu=findViewById(R.id.et1);
        eTpd=findViewById(R.id.et2);
        //设置监听,方法二与固定的字符串匹配登录
        mBtnLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }
    public void onClick( View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent intent=new Intent(this,Register.class);
                startActivity(intent);
                break;
        }
    }
    //登录
    public void login(){
        //获取输入框中的字符串
        String Username = eTu.getText().toString();
        String pwd = eTpd.getText().toString();
        if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
        } else{
            User user = new User(Username,pwd);
            Dao dao = new Dao(this);
            int result = dao.queryUserLogin(user);
            if (result == 0) {
                Toast.makeText(this, "用户或密码错误，登陆失败", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "欢迎"+Username, Toast.LENGTH_SHORT).show();
                //
                //将信息使用sharedpreferences保存
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putInt("id",result);
                editor.putString("username",Username);
                editor.putString("passsword",pwd);
                editor.commit();
                //跳转到功能页面
                Intent intent=new Intent(this,FunctionActivity.class);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}