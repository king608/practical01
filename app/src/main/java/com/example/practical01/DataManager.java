package com.example.practical01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.example.Mysystem.SysApplication;
import com.example.SQLiteDatabase.IncomeClass;
import com.example.SQLiteDatabase.IncomeDao;
import com.example.SQLiteDatabase.OutcomeClass;
import com.example.SQLiteDatabase.OutcomeDao;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class DataManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager);
        //将该activity添加到list中去,想关闭时，调用SysApplication的exit方法
        SysApplication.getInstance().addActivity(this);
        //初始化控件
        Button btn_output_out=findViewById(R.id.btn_output_out);
        Button btn_output_in=findViewById(R.id.btn_output_in);
        TextView show_path=findViewById(R.id.show_path);
        ImageView iv_back15=findViewById(R.id.iv_back15);
        //返回键
        iv_back15.setOnClickListener(new View.OnClickListener() {
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

        //从SQLite数据库中读出支出数据。
        btn_output_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从SQLite数据库中读出支出数据。
                OutcomeDao dao=new OutcomeDao(DataManager.this);
                ArrayList<OutcomeClass> hh=new ArrayList<OutcomeClass>();
                //
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                int uid=sp.getInt("id",0);
                hh=dao.queryOutcome(uid);
                if(hh!=null){
                    //调用函数创建EXCEL
                    Calendar c=Calendar.getInstance();
                    //获取当前日期
                    String date=c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH)+
                            " "+c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.SECOND);
                    //Log.i("date",date);
                    creatOutExcel(hh,"outcome"+date);
                }else{
                    Toast.makeText(getApplicationContext(),"导出失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //从SQLite数据库中读出收入数据。
        btn_output_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从SQLite数据库中读出收入数据。
                IncomeDao in=new IncomeDao(getApplicationContext());
                ArrayList<IncomeClass> list=new ArrayList<IncomeClass>();
                //
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                int uid=sp.getInt("id",0);
                list=in.queryIncome(uid);
                if(list!=null){
                    //调用函数创建EXCEL
                    Calendar c=Calendar.getInstance();
                    //获取当前日期
                    String date=c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH)+
                            " "+c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.SECOND);
                    creatInExcel(list,"income"+date);
                }else{
                    Toast.makeText(getApplicationContext(),"数据为空,导出失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //创建收入EXCEL的函数
    private void creatInExcel(ArrayList<IncomeClass> list,String excelName){
        //创建EXCEL工作薄
        HSSFWorkbook mWorkbook = new HSSFWorkbook();
        //创建EXCEL表
        HSSFSheet mSheet = mWorkbook.createSheet(excelName);
        //第一行的字段创建
        createInExcelHead(mSheet);
        //将查询的数据逐行赋值到EXCEL中
        for (IncomeClass incomeClass : list) {
            createInCell(incomeClass.getTime(),incomeClass.getAmount(),
                    incomeClass.getCategory(),incomeClass.getFuKuanRen(),
                    incomeClass.getBeiZhu(),mSheet);
        }
        //文件存放路径
        String pathname="/sdcard/Documents/"+excelName+".xls";
        File xlsFile = new File(pathname);
        try {
            if (!xlsFile.exists()) {
                xlsFile.createNewFile();
            }
            //mWorkbook.write(xlsFile);
            // 或者以流的形式写入文件
            mWorkbook.write(new FileOutputStream(xlsFile));
            Toast.makeText(getApplicationContext(),"导出成功",Toast.LENGTH_SHORT).show();
            TextView showpath=findViewById(R.id.show_path);
            showpath.setText("文件路径为"+pathname);
            mWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 创建支出Excel表标题行，第一行。
    private void createInExcelHead(HSSFSheet mSheet) {
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue("时间");
        headRow.createCell(1).setCellValue("金额/元");
        headRow.createCell(2).setCellValue("分类");
        headRow.createCell(3).setCellValue("付款人");
        headRow.createCell(4).setCellValue("备注");
    }
    // // 创建支出Excel的一行数据。
    private void createInCell(String time, String amount, String category,String fuKanRen,String beiZhu, HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(time);
        dataRow.createCell(1).setCellValue(amount);
        dataRow.createCell(2).setCellValue(category);
        dataRow.createCell(3).setCellValue(fuKanRen);
        dataRow.createCell(4).setCellValue(beiZhu);
    }



    //创建支出EXCEL的函数
    private void creatOutExcel(ArrayList<OutcomeClass> list,String excelName){
        //创建EXCEL工作薄
        HSSFWorkbook mWorkbook = new HSSFWorkbook();
        //创建EXCEL表
        HSSFSheet mSheet = mWorkbook.createSheet(excelName);
        //第一行的字段创建
        createExcelHead(mSheet);
        //将查询的数据逐行赋值到EXCEL中
        for (OutcomeClass outcomeClass : list) {
            createCell(outcomeClass.getTime(), outcomeClass.getAmount(), outcomeClass.getCategory(),outcomeClass.getBeiZhu(), mSheet);
        }
        //文件存放路径
        String pathname="/sdcard/Documents/"+excelName+".xls";
        File xlsFile = new File(pathname);
        try {
            if (!xlsFile.exists()) {
                xlsFile.createNewFile();
            }
            //mWorkbook.write(xlsFile);
            // 或者以流的形式写入文件
            mWorkbook.write(new FileOutputStream(xlsFile));
            Toast.makeText(getApplicationContext(),"导出成功",Toast.LENGTH_SHORT).show();
            TextView showpath=findViewById(R.id.show_path);
            showpath.setText("文件路径为"+pathname);
            mWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 创建支出Excel表标题行，第一行。
    private void createExcelHead(HSSFSheet mSheet) {
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue("时间");
        headRow.createCell(1).setCellValue("金额/元");
        headRow.createCell(2).setCellValue("分类");
        headRow.createCell(3).setCellValue("备注");
    }

    // 创建支出Excel的一行数据。
    private static void createCell(String time, String amount, String category, String beiZhu, HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(time);
        dataRow.createCell(1).setCellValue(amount);
        dataRow.createCell(2).setCellValue(category);
        dataRow.createCell(3).setCellValue(beiZhu);
    }


}