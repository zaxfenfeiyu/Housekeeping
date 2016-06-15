package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.SecondKillEntity;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SecKillPlaceOrderActivity extends Activity implements View.OnClickListener{
    private ImageButton returnBtn;
    private SecondKillEntity se;
    private Button TimeBtn;
    private  Button DateBtn;
    private  Button AddressBtn;
    private  Button MessageBtn;
    private Button SubmitBtn;
    private Calendar calendar;
    private  String time;
    private  String date;
    private  String address;
    private String message;
    private EditText et;
    private String returnRes;

    private ProgressDialog progressDialog;
    String urlPath = "http://"+Data.ip+":8080/HouseKeeping/placeSecondKillOrder.action";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progressDialog.dismiss();
                    if (returnRes.equals("succeed")) {
                        Toast.makeText(getApplicationContext(), "下单成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SecKillPlaceOrderActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(returnRes.equals("failed")){
                        Toast.makeText(getApplicationContext(), "很遗憾未能抢到", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SecKillPlaceOrderActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "服务器错误!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_kill_place_order);
        returnBtn = (ImageButton) findViewById(R.id.sec_place_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        se=(SecondKillEntity)intent.getSerializableExtra("secondKill");

        TimeBtn = (Button) findViewById(R.id.btn_sec_place_time);
        TimeBtn.setOnClickListener(this);
        AddressBtn = (Button) findViewById(R.id.btn_sec_place_address);
        AddressBtn.setOnClickListener(this);
        MessageBtn = (Button) findViewById(R.id.btn_sec_place_message);
        MessageBtn.setOnClickListener(this);
        SubmitBtn = (Button) findViewById(R.id.btn_sec_place_submit);
        SubmitBtn.setOnClickListener(this);
        DateBtn = (Button) findViewById(R.id.btn_sec_place_date);
        DateBtn.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sec_place_date:
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(
                        SecKillPlaceOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        monthOfYear++;
                        date = year + "-" + (monthOfYear > 9 ? monthOfYear : "0" + monthOfYear) + "-" + (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth);
                        DateBtn.setText(date);
                    }
                }, year, month, day).show();
                break;
            case R.id.btn_sec_place_time:
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(SecKillPlaceOrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        time = (hourOfDay > 9 ? hourOfDay : "0" + hourOfDay) + ":" + (minute > 9 ? minute : "0" + minute) + ":00";
                        TimeBtn.setText(time);
                    }
                }, hour, minute, true).show();
                break;
            case R.id.btn_sec_place_address:
                et = new EditText(this);
                et.setBackgroundResource(R.drawable.edit_shape);

                et.setText(Data.getAddress());
                new AlertDialog.Builder(this).setTitle("填写地址")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "地址不能为空！" + input, Toast.LENGTH_LONG).show();
                                } else {
                                    AddressBtn.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.btn_sec_place_message:
                et = new EditText(this);
                et.setBackgroundResource(R.drawable.edit_shape);
                new AlertDialog.Builder(this).setTitle("留言")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (!input.equals("")) {
                                    MessageBtn.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.btn_sec_place_submit:
                progressDialog = ProgressDialog.show(SecKillPlaceOrderActivity.this,"","正在下单，请稍后");
                progressDialog.setCancelable(true);
                if (validate()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("id",String.valueOf(se.getId()));
                                map.put("re_id",Data.getRe_id());
                                map.put("pro_id",String.valueOf(se.getPro_id()));
                                map.put("sc_id",String.valueOf(se.getSc_id()) );
                                map.put("address",address);
                                map.put("time",date+" "+time);
                                map.put("message",message);
                                map.put("price","0.0");
                                InputStream inputStream = RequestService.postRequest(urlPath, map);
                                returnRes = RequestService.dealResponseResult(inputStream);
                                handler.sendEmptyMessage(0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
        }
    }
    public boolean validate() {
        date = DateBtn.getText().toString().trim();
        time = TimeBtn.getText().toString().trim();
        address = AddressBtn.getText().toString().trim();
        message = MessageBtn.getText().toString().trim();
        Date date1=new Date();
        Date ServiceDate=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            ServiceDate = format.parse(date +" "+ time);
        }catch(ParseException e){
            e.printStackTrace();
        }
        if (date.equals("请选择开始日期")) {
            Toast.makeText(getApplicationContext(), "请选择开始日期", Toast.LENGTH_LONG).show();
            return false;
        } else if (time.equals("请选择开始时间")) {
            Toast.makeText(getApplicationContext(), "请选择开始时间", Toast.LENGTH_LONG).show();
            return false;
        } else if (address.equals("请输入服务地址")) {
            Toast.makeText(getApplicationContext(), "请输入服务地址", Toast.LENGTH_LONG).show();
            return false;
        } else if(ServiceDate.getTime()<date1.getTime()){
            Toast.makeText(getApplicationContext(), "服务时间不能早于现在的时间！", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }
}
