package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.OrderDetail;
import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

/**
 * Created by ariel on 2016/4/26.
 */
public class ProviderDetailActivity extends Activity {
    private OrderDetail orderDetail;
    private ProviderEntity pe;
    private TextView nameText;
    private TextView introductionText;
    private TextView phoneText;
    private TextView rankText;
    private Button submitBtn;
    private ImageButton collectBtn;
    private String returnRes;
    private String urlPath="http://192.168.22.1:8080/HouseKeeping/placeOrder.action";
   // private String urlPath="http://192.168.134.1:8080/HouseKeeping/placeOrder.action";
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progressDialog.dismiss();
                    if(returnRes.equals("succeed")){
                        Toast.makeText(getApplicationContext(), "下单成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProviderDetailActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "服务器错误!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_datail);

        Intent intent = getIntent();
        orderDetail = (OrderDetail) intent.getSerializableExtra("orderDetail");
        pe = (ProviderEntity) intent.getSerializableExtra("provider");
        nameText=(TextView)findViewById(R.id.text_name);
        introductionText=(TextView)findViewById(R.id.text_introduction);
        phoneText=(TextView)findViewById(R.id.text_phone);
        rankText=(TextView)findViewById(R.id.text_rank);

        nameText.setText(pe.getName());
        introductionText.setText(pe.getIntroduction());
        phoneText.setText(pe.getPhone());
        rankText.setText(String.valueOf(pe.getRank()));


        submitBtn=(Button)findViewById(R.id.btn_submit) ;
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(ProviderDetailActivity.this,"","正在下单，请稍后");
                progressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("re_id",Data.getRe_id());
                            map.put("pro_id",String.valueOf(pe.getId()));
                            map.put("sc_id",String.valueOf(orderDetail.getSc_id()) );
                            map.put("address",orderDetail.getAddress());
                            map.put("time",orderDetail.getTime());
                            map.put("message",orderDetail.getMessage());
                            map.put("price",String.valueOf(orderDetail.getPrice()));
                            InputStream inputStream = RequestService.postRequest(urlPath, map);
                            returnRes = RequestService.dealResponseResult(inputStream);
                            handler.sendEmptyMessage(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        collectBtn=(ImageButton)findViewById(R.id.btn_collect); //收藏按钮
        collectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                @Override
                    public void run(){
                    try{
                        Map<String,String> map=new HashMap<String, String>();

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                }).start();
            }
        });
    }
}
