package com.example.ariel.housekeeping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RecProviderDetailActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView introductionText;
    private TextView phoneText;
    private TextView rankText;
    private TextView serviceText;
    private ImageButton returnBtn;
    private ProviderEntity pe;
    private String returnRes;
    private ProgressDialog progressDialog;
    private String urlPath2="http://"+Data.ip+":8080/HouseKeeping/collectProvider.action";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                progressDialog.dismiss();
                if(returnRes.equals("succeed")) {
                    Toast.makeText(getApplicationContext(), "收藏成功!", Toast.LENGTH_SHORT).show();
                }
                else if(returnRes.equals("exist"))
                    Toast.makeText(getApplicationContext(), "成功取消收藏!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "服务器错误!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_detail);

        nameText=(TextView) findViewById(R.id.text_rec_name);
        introductionText=(TextView) findViewById(R.id.text_rec_introduction);
        phoneText=(TextView) findViewById(R.id.text_rec_phone);
        rankText=(TextView) findViewById(R.id.text_rec_rank);
        serviceText=(TextView) findViewById(R.id.text_rec_service);

        returnBtn = (ImageButton) findViewById(R.id.rec_det_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent =getIntent();
        pe=(ProviderEntity)intent.getSerializableExtra("provider");

        nameText.setText(pe.getName());
        introductionText.setText(pe.getIntroduction());
        phoneText.setText(pe.getPhone());
        rankText.setText(String.valueOf(pe.getRank()));
        serviceText.setText(pe.getService());

        ImageButton collectBtn=(ImageButton)findViewById(R.id.btn_collect); //收藏按钮
        collectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                progressDialog = ProgressDialog.show(RecProviderDetailActivity.this,"","正在收藏，请稍后");
                progressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run(){
                        try{
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("re_id",Data.getRe_id());
                            map.put("pro_id",String.valueOf(pe.getId()));
                            InputStream inputStream = RequestService.postRequest(urlPath2, map);
                            returnRes = RequestService.dealResponseResult(inputStream);
                            handler.sendEmptyMessage(1);
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
