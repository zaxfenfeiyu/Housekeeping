package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.OrderDetail2;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ariel on 2016/4/27.
 */
public class OrderDetailActivity extends Activity {
    private int order_id;
    private String state;
    private TextView proNameText;
    private TextView stscNameText;
    private TextView priceText;
    private TextView resNameText;
    private TextView phoneText;
    private TextView serviceTimeText;
    private TextView addressText;
    private TextView placeTimeText;
    private TextView messageText;
    private Button complainBtn;
    private Button remarkBtn;
    private Button confirmBtn;
    private String resultRes;
    private List<OrderDetail2> list;

    private String urlPath = "http://192.168.134.50:8080/HouseKeeping/orderDetail.action";
    //private String urlPath = "http://192.168.2.105:8080/HouseKeeping/orderDetail.action";
    private String urlPath2 = "http://192.168.134.50:8080/HouseKeeping/confirmOrder.action";
    //private String urlPath2 = "http://192.168.2.105:8080/HouseKeeping/confirmOrder.action";
//
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (list.size() > 0) {
                        OrderDetail2 od = list.get(0);
                        proNameText.setText(od.getPro_name());
                        stscNameText.setText(od.getStsc_name());
                        priceText.setText(String.valueOf(od.getPrice()));
                        resNameText.setText(od.getRe_name());
                        phoneText.setText(od.getRe_phone());
                        serviceTimeText.setText(od.getService_time());
                        addressText.setText(od.getAddress());
                        placeTimeText.setText(od.getPlace_time());
                        messageText.setText(od.getMessage());
                    }
                    break;
                case 1:
                    if(resultRes.equals("succeed")){
                        Toast.makeText(getApplicationContext(), "确认成功!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(OrderDetailActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "确认失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        proNameText = (TextView) findViewById(R.id.text_provider_name);
        stscNameText = (TextView) findViewById(R.id.text_st_sc);
        priceText = (TextView) findViewById(R.id.text_price);
        resNameText = (TextView) findViewById(R.id.text_resident_name);
        phoneText = (TextView) findViewById(R.id.text_resident_phone);
        serviceTimeText = (TextView) findViewById(R.id.text_service_time);
        addressText = (TextView) findViewById(R.id.text_address);
        placeTimeText = (TextView) findViewById(R.id.text_place_time);
        messageText = (TextView) findViewById(R.id.text_message);
        complainBtn = (Button) findViewById(R.id.btn_complain);
        remarkBtn = (Button) findViewById(R.id.btn_remark);
        confirmBtn = (Button) findViewById(R.id.btn_confirm);
        complainBtn.setOnClickListener(BtnListener);
        remarkBtn.setOnClickListener(BtnListener);
        confirmBtn.setOnClickListener(BtnListener);
        remarkBtn.setVisibility(View.INVISIBLE);
        confirmBtn.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);
        state = intent.getStringExtra("state");
        if (state.equals("待确认")) {
            confirmBtn.setVisibility(View.VISIBLE);
        } else if (state.equals("待评价")) {
            remarkBtn.setVisibility(View.VISIBLE);
        }
        getOrderDetail();
    }

    public void getOrderDetail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("order_id", String.valueOf(order_id));
                    InputStream inputStream = RequestService.postRequest(urlPath, map);
                    if (inputStream == null) {
                        handler.sendEmptyMessage(-1);
                    } else {
                        String str = RequestService.dealResponseResult(inputStream);
                        list = RequestService.orderDetailJSON(str);
                        handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Button.OnClickListener BtnListener = new Button.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_complain:

                    break;
                case R.id.btn_remark:
                    Intent intent = new Intent(OrderDetailActivity.this, RemarkActivity.class);
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("pro_name", proNameText.getText().toString().trim());
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn_confirm:
                    new AlertDialog.Builder(OrderDetailActivity.this).setMessage("是否确认完成")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Map<String, String> map = new HashMap<String, String>();
                                                map.put("order_id", String.valueOf(order_id));
                                                InputStream inputStream = RequestService.postRequest(urlPath2, map);
                                                if (inputStream == null) {
                                                    handler.sendEmptyMessage(-1);
                                                } else {
                                                    resultRes = RequestService.dealResponseResult(inputStream);
                                                    handler.sendEmptyMessage(1);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
            }
        }
    };
}
