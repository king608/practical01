package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Mysystem.SysApplication;
import com.example.SQLiteDatabase.OutcomeClass;
import com.example.SQLiteDatabase.OutcomeDao;

import java.util.Calendar;

public class Outcome extends AppCompatActivity {
    private Spinner Spinner_out_category;
    private String[] cate2=new String[20];
    private int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);

        //分类
        Spinner_out_category = findViewById(R.id.Spinner_out_category);
        //设置下拉框的数组适配器
        cate2 = getResources().getStringArray(R.array.out_category);
        //设置下拉框默认的显示第一项
        Spinner_out_category.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        Spinner_out_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Outcome.this,"您选择的分类是：  "+cate2[i],Toast.LENGTH_SHORT).show();
                k=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //返回键
        ImageView iv_back11=findViewById(R.id.iv_back11);
        iv_back11.setOnClickListener(new View.OnClickListener() {
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
        //时间
        Button dateBtn = (Button) findViewById(R.id.out_date);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Calendar c = Calendar.getInstance();
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(Outcome.this,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateBtn.setText( year + "-" + monthOfYear
                                        + "-" + dayOfMonth);
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //提交保存,插入数据
        Button out_server=findViewById(R.id.out_server);
        out_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取数据
                //金额
                EditText et_out_amount=findViewById(R.id.et_out_amount);
                String amount=et_out_amount.getText().toString().trim();
                //时间
                Button out_date=findViewById(R.id.out_date);
                String date=out_date.getText().toString().trim();
                //类别
                cate2 = getResources().getStringArray(R.array.out_category);
                String category=cate2[k];
                //备注
                EditText et_out_beiZhu=findViewById(R.id.et_out_beiZhu);
                String beiZhu=et_out_beiZhu.getText().toString().trim();
                //检查输入是否符合规则
                if((!amount.isEmpty())&&(!date.equals("点击选择时间"))&&(!category.isEmpty())){
                    //从sharedpreferences中获取uid
                    SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                    int uid=sp.getInt("id",0);
                    //Log.i("amount",amount);
                    //Log.i("category",category);
                    //Log.i("date",date);
                    if(uid==0){
                        Toast.makeText(Outcome.this,"保存失败！",Toast.LENGTH_SHORT).show();
                    }else{
                        OutcomeClass out=new OutcomeClass(uid,date,amount,category,beiZhu);
                        OutcomeDao dao=new OutcomeDao(Outcome.this);
                        long i=dao.insertOutcome(out);
                        if(i>0){
                            Toast.makeText(Outcome.this,"保存成功！",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Outcome.this,"保存失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    //检查输入不符合规则
                    Toast.makeText(Outcome.this,"请保证金额与时间、分类不为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}