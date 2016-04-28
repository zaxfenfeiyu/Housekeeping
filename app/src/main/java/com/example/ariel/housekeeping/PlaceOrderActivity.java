package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.OrderDetail;
import com.example.ariel.housekeeping.entity.ProviderEntity;
import com.example.ariel.housekeeping.entity.ServicecatalogEntity;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2016/4/23.
 */
public class PlaceOrderActivity extends Activity implements View.OnClickListener {
    AlertDialog.Builder builder;
    TextView title, TotalMoney;
    Button ChooseTypeBtn;
    Button TimeBtn;
    Button DateBtn;
    Button AddressBtn;
    Button MessageBtn;
    Button SubmitBtn;
    EditText et;
    String chooseType;
    String time;
    String date;
    String address;
    String message;
    int scID;
    double price;
    String urlPath1 = "http://115.200.28.77:8080/HouseKeeping/getSCbyST.action";
    String urlPath2 = "http://115.200.28.77:8080/HouseKeeping/getSCProviders.action";
    ServicecatalogAdapter scAdapter;
    private Calendar calendar;
    private List<ServicecatalogEntity> sclist;
    private List<ProviderEntity> plist;
    private OrderDetail od;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    scAdapter = new ServicecatalogAdapter(getApplicationContext(), sclist);
                    builder.setAdapter(scAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ServicecatalogEntity pe = (ServicecatalogEntity) scAdapter.getItem(which);
                            Toast.makeText(PlaceOrderActivity.this, "选择的分类为：" + pe.getName(), Toast.LENGTH_SHORT).show();
                            scID = pe.getId();
                            ChooseTypeBtn.setText(pe.getName());
                            price=pe.getPrice();
                            TotalMoney.setText(String.valueOf(pe.getPrice()));
                        }
                    });
                    builder.show();
                    break;
                case 1:
                    Intent intent = new Intent(PlaceOrderActivity.this, ProviderActivity.class);
                    intent.putExtra("plist", (Serializable) plist);
                    intent.putExtra("orderDetail", od);
                    startActivity(intent);
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_muying);

        //根据用户选择的按钮更改该界面的标题
        title = (TextView) findViewById(R.id.textView1);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        title.setText(bundle.getString("type"));

        TotalMoney = (TextView) findViewById(R.id.text_totalMoney);
        ChooseTypeBtn = (Button) findViewById(R.id.btn_subclass);
        ChooseTypeBtn.setOnClickListener(this);
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
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_subclass:
                builder = new AlertDialog.Builder(PlaceOrderActivity.this);
                builder.setIcon(R.drawable.home2);
                builder.setTitle("选择一个家政分类");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("st_name", title.getText().toString());
                            InputStream inputStream = RequestService.postRequest(urlPath1, map);
                            String str = RequestService.dealResponseResult(inputStream);
                            sclist = RequestService.servicecatalogJSON(str);
                            handler.sendEmptyMessage(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_date:
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(
                        PlaceOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                new TimePickerDialog(PlaceOrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                new AlertDialog.Builder(this).setTitle("留言")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (!input.equals("")) {
                                    AddressBtn.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.btn_submit:
                if (validate()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("sc_id", String.valueOf(scID));
                                od = new OrderDetail(scID, address, date+" "+time, message,price);
                                InputStream inputStream = RequestService.postRequest(urlPath2, map);
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
        chooseType = ChooseTypeBtn.getText().toString().trim();
        date = DateBtn.getText().toString().trim();
        time = TimeBtn.getText().toString().trim();
        address = AddressBtn.getText().toString().trim();
        message = MessageBtn.getText().toString().trim();
        if (chooseType.equals("请选择子分类")) {
            Toast.makeText(getApplicationContext(), "请选择子类", Toast.LENGTH_LONG).show();
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

