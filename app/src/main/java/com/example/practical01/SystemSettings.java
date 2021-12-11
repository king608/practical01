package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Mysystem.SysApplication;
import com.example.SQLiteDatabase.Dao;
import com.example.SQLiteDatabase.IncomeDao;
import com.example.SQLiteDatabase.OutcomeDao;

public class SystemSettings extends AppCompatActivity{
    private EditText et_Password1;
    private EditText et_Password2;
    private Button btn_t;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_settings);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);
        //初始化
        et_Password1=findViewById(R.id.et_Password1);
        et_Password2=findViewById(R.id.et_Password2);
        btn_t=(Button) findViewById(R.id.btn_t);
        back=findViewById(R.id.iv_back);
        //返回键
        back.setOnClickListener(new View.OnClickListener() {
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
        //修改
        btn_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd1=et_Password1.getText().toString().trim();
                String pwd2=et_Password2.getText().toString().trim();
                if(pwd1.equals(pwd2)){
                    showNormalDialog();
                }else{
                    Toast.makeText(SystemSettings.this, "新密码与确认密码不一致请检查！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //删除所有该用户的收入记录
        Button btn_in_delete=findViewById(R.id.btn_in_delete);
        btn_in_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteIncomeDialog();
            }
        });
        //删除所有该用户的支出记录
        Button btn_out_delete=findViewById(R.id.btn_out_delete);
        btn_out_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteOutcomeDialog();
            }
        });
    }


    //删除支出记录
    private void showDeleteOutcomeDialog(){
        AlertDialog.Builder deleteDialog1=new AlertDialog.Builder(SystemSettings.this);
        deleteDialog1.setIcon(R.drawable.dialog);
        deleteDialog1.setTitle("温馨提示");
        deleteDialog1.setMessage("您确定要删除所有支出记录吗？");
        deleteDialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //获取用户名uid
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                int uid=sp.getInt("id",0);
                OutcomeDao out=new OutcomeDao(SystemSettings.this);
                int h=out.deleteOutcome(uid);
                if(h>0){
                    Toast.makeText(SystemSettings.this,"删除成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SystemSettings.this,"删除失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteDialog1.setNegativeButton("取消", null);
        deleteDialog1.show();
    }
    //删除收入记录
    private void showDeleteIncomeDialog(){
        AlertDialog.Builder deleteDialog=new AlertDialog.Builder(SystemSettings.this);
        deleteDialog.setIcon(R.drawable.dialog);
        deleteDialog.setTitle("温馨提示");
        deleteDialog.setMessage("您确定要删除所有收入记录吗？");
        deleteDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //获取用户名uid
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                int uid=sp.getInt("id",0);
                Log.i("uid",uid+"");
                IncomeDao dao=new IncomeDao(SystemSettings.this);
                int h=dao.deleteIncome(uid);
                if(h>0){
                    Toast.makeText(SystemSettings.this,"删除成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SystemSettings.this,"删除失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
        deleteDialog.setNegativeButton("取消", null);
        deleteDialog.show();

    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        AlertDialog.Builder normalDialog =new AlertDialog.Builder(SystemSettings.this);
        normalDialog.setIcon(R.drawable.dialog);
        normalDialog.setTitle("温馨提示");
        normalDialog.setMessage("您确定要修改密码吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_Password1=findViewById(R.id.et_Password1);
                        String password=et_Password1.getText().toString().trim();
                        //
                        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                        String username=sp.getString("username","");
                        Log.i("查找用户名成功",username);
                        //根据用户名修改密码。
                        Dao dao=new Dao(SystemSettings.this);
                        int i=dao.updateUser(username,password);
                        if(i!=-1){
                            //将信息使用sharedpreferences保存
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putString("username",username);
                            editor.putString("passsword",password);
                            editor.commit();
                            Toast.makeText(SystemSettings.this, "密码修改成功,请重新登录！", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SystemSettings.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SystemSettings.this, "密码修改失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        normalDialog.setNegativeButton("取消", null);
        // 显示
        normalDialog.show();
    }




}