package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.OrderDetail;
import com.example.ariel.housekeeping.entity.ProviderEntity;
import com.example.ariel.housekeeping.entity.ServicecatalogEntity;


import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceVIPOrderActivity extends Activity implements View.OnClickListener{
    String urlPath = "http://"+Data.ip+":8080/HouseKeeping/getSCProviders.action";
    AlertDialog.Builder builder;
    Button SubVIPService;
    Button TimeBtn;
    Button DateBtn;
    Button AddressBtn;
    Button MessageBtn;
    Button SubmitBtn;
    private ImageButton returnBtn;
    EditText et;
    String VIPService;
    String time;
    String date;
    String address;
    String message;
    int scID=23;
    private Calendar calendar;
    private List<ProviderEntity> plist;
    private OrderDetail od;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(PlaceVIPOrderActivity.this, ProviderActivity.class);
                    intent.putExtra("plist", (Serializable) plist);
                    intent.putExtra("orderDetail", od);
                    startActivity(intent);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_viporder);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        SubVIPService = (Button) findViewById(R.id.btn_subVipService);
        SubVIPService.setOnClickListener(this);
        TimeBtn = (Button) findViewById(R.id.btn_time);
        TimeBtn.setOnClickListener(this);
        AddressBtn = (Button) findViewById(R.id.btn_address);
        AddressBtn.setOnClickListener(this);
        MessageBtn = (Button) findViewById(R.id.btn_message);
        MessageBtn.setOnClickListener(this);
        SubmitBtn = (Button) findViewById(R.id.btn_submit);
        SubmitBtn.setOnClickListener(this);
        DateBtn = (Button) findViewById(R.id.btn_date);
        DateBtn.setOnClickListener(this);

        //监听返回键
        returnBtn = (ImageButton) findViewById(R.id.btn_place_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_subVipService:
                et = new EditText(this);
                VIPService = SubVIPService.getText().toString().trim();
                et.setText(VIPService);
                et.setBackgroundResource(R.drawable.edit_shape);
                new AlertDialog.Builder(this).setTitle("填写自定义服务描述")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if(input.equals(""))
                                {
                                    Toast.makeText(getApplicationContext(), "描述不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    SubVIPService.setText(input);
                                    MessageBtn.setText(input);
                                }
                                try
                                {
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    //设置mShowing值，欺骗android系统
                                    field.set(dialog, true);
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNeutralButton("清空",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try
                                {
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    //设置mShowing值，欺骗android系统
                                    field.set(dialog, false);
                                }catch(Exception e) {
                                    e.printStackTrace();
                                }
                                et.setText("");
                            }
                        })
                        .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try
                                {
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    //设置mShowing值，欺骗android系统
                                    field.set(dialog, true);
                                }catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .show();
                break;
            case R.id.btn_date:
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(
                        PlaceVIPOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        monthOfYear++;
                        date = year + "-" + (monthOfYear > 9 ? monthOfYear : "0" + monthOfYear) + "-" + (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth);
                        DateBtn.setText(date);
                    }
                }, year, month, day).show();
                break;
            case R.id.btn_time:
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(PlaceVIPOrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            case R.id.btn_address:
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
            case R.id.btn_message:
                et = new EditText(this);
                et.setBackgroundResource(R.drawable.edit_shape);
                et.setText(MessageBtn.getText().toString());
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
            case R.id.btn_submit:
                if (validate()) {
                    if(Data.getIfLogin()==false)
                    {
                        Toast.makeText(this,"请先登录！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("sc_id", String.valueOf(scID));
                                map.put("re_account", Data.getUsername());
                                od = new OrderDetail(scID, address, date+" "+time, message,0);
                                InputStream inputStream = RequestService.postRequest(urlPath, map);
                                String str = RequestService.dealResponseResult(inputStream);
                                plist = RequestService.providerJSON(str);
                                handler.sendEmptyMessage(1);
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
        VIPService = SubVIPService.getText().toString().trim();
        date = DateBtn.getText().toString().trim();
        time = TimeBtn.getText().toString().trim();
        address = AddressBtn.getText().toString().trim();
        message = MessageBtn.getText().toString().trim();
        if (VIPService.equals("请输入自定义服务描述")) {
            Toast.makeText(getApplicationContext(), "请输入自定义服务描述", Toast.LENGTH_LONG).show();
            return false;
        } else if (date.equals("请选择开始日期")) {
            Toast.makeText(getApplicationContext(), "请选择开始日期", Toast.LENGTH_LONG).show();
            return false;
        } else if (time.equals("请选择开始时间")) {
            Toast.makeText(getApplicationContext(), "请选择开始时间", Toast.LENGTH_LONG).show();
            return false;
        } else if (address.equals("请输入服务地址")) {
            Toast.makeText(getApplicationContext(), "请输入服务地址", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
