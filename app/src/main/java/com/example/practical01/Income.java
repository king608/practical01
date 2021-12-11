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
import com.example.SQLiteDatabase.IncomeClass;
import com.example.SQLiteDatabase.IncomeDao;

import java.util.Calendar;

public class Income extends AppCompatActivity {
    private EditText et_in_amount;
    private Spinner et_in_category;
    private EditText et_in_fuKuan;
    private EditText et_in_beiZhu;
    private Button btn_server;
    private ImageView iv_back12;
    private String[] cate=new String[20];
    private int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);

        //获取array中定义的值
        //分类
        et_in_category = findViewById(R.id.et_in_category);
        //设置下拉框的数组适配器
        cate = getResources().getStringArray(R.array.in_category);
        //设置下拉框默认的显示第一项
        et_in_category.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        et_in_category.setOnItemSelectedListener(new MySelectedListener1());

        //时间
        //时间选择器DatePicker
        Button dateBtn = (Button) findViewById(R.id.id_date);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Calendar c = Calendar.getInstance();
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(Income.this,
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

        //返回键
        iv_back12=findViewById(R.id.iv_back12);
        iv_back12.setOnClickListener(new View.OnClickListener() {
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
        btn_server=findViewById(R.id.btn_server);
        btn_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //金额
                et_in_amount=findViewById(R.id.et_in_amount);
                String amount=et_in_amount.getText().toString().trim();
                //获取时间
                Button dateBt=findViewById(R.id.id_date);
                String date=dateBt.getText().toString().trim();
                //付款方
                et_in_fuKuan=findViewById(R.id.et_in_fuKuan);
                String fuKuan=et_in_fuKuan.getText().toString().trim();
                //备注
                et_in_beiZhu=findViewById(R.id.et_in_beiZhu);
                String beiZhu=et_in_beiZhu.getText().toString().trim();
                //分类
                et_in_category = findViewById(R.id.et_in_category);
                cate = getResources().getStringArray(R.array.in_category);
                String category=cate[k];

                //检查输入是否符合规则
                if((!amount.isEmpty())&&(!date.equals("点击选择时间"))&&(!category.isEmpty())){
                    //从sharedpreferences中获取uid
                    SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                    int uid=sp.getInt("id",0);
                    if(uid==0){
                        Toast.makeText(Income.this,"保存失败！",Toast.LENGTH_SHORT).show();
                    }else{
                        IncomeClass in=new IncomeClass(uid, date,amount,category,fuKuan,beiZhu);
                        IncomeDao inc=new IncomeDao(Income.this);
                        long i=inc.insertIncome(in);
                        if(i>0){
                            Toast.makeText(Income.this,"保存成功！",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Income.this,"保存成功！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    //检查输入不符合规则
                    Toast.makeText(Income.this,"请保证金额与时间、分类不为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }




    //分类选择
    class MySelectedListener1 implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(Income.this,"您选择的分类是：  "+cate[i],Toast.LENGTH_SHORT).show();
            k=i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}