package com.example.ariel.housekeeping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.OrderDetail;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AdviceActivity extends AppCompatActivity {

    EditText advice;
    Button submitBtn;
    String result;
    private ImageButton returnBtn;
    private ProgressDialog progressDialog;
    String urlPath="http://"+Data.ip+":8080/HouseKeeping/addAdvice.action";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(result.equals("succeed"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "提交成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdviceActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "提交失败!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        advice=(EditText)findViewById(R.id.add_content);
        //监听返回键
        returnBtn = (ImageButton) findViewById(R.id.btn_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitBtn=(Button)findViewById(R.id.btn_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String adviceContent=advice.getText().toString();
                progressDialog = ProgressDialog.show(AdviceActivity.this,"","正在提交，请稍后");
                progressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("re_id", Data.getRe_id());
                            map.put("adviceContent", adviceContent);
                            InputStream inputStream = RequestService.postRequest(urlPath, map);
                            if (inputStream == null) {
                                handler.sendEmptyMessage(-1);
                            }else{
                            result = RequestService.dealResponseResult(inputStream);
                            handler.sendEmptyMessage(1);}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Intent intent = new Intent(getApplication(), AdviceActivity.class);
                startActivity(intent);
            }
        });
    }

}
