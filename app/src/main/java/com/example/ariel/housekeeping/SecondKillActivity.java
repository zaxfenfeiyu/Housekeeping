package com.example.ariel.housekeeping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.SecondKillEntity;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class SecondKillActivity extends AppCompatActivity {
    private ImageButton returnBtn;
    private ListView listView;
    private SecondKillAdapter ska;
    private TextView noData;
    private ProgressDialog progressDialog;
    private List<SecondKillEntity> slist;
    private String urlPath="http://"+Data.ip+":8080/HouseKeeping/getSecondKills.action";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progressDialog.dismiss();
                    ska = new SecondKillAdapter(getApplicationContext(), slist);
                    listView.setAdapter(ska);
                    listView.setOnItemClickListener(itemListener);
                    break;
                case -1:
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_kill);

        noData=(TextView)findViewById(R.id.sec_empty) ;
        returnBtn = (ImageButton) findViewById(R.id.secKill_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.secKill_listView);
        listView.setEmptyView(noData);

        getSecondKill();

    }

    public void getSecondKill() {
        progressDialog = ProgressDialog.show(SecondKillActivity.this, "", "正在获取，请稍后");
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream=RequestService.getJSONData(urlPath);
                    if (inputStream == null) {
                        handler.sendEmptyMessage(-1);
                    } else {
                        String str = RequestService.dealResponseResult(inputStream);
                        slist=RequestService.secondKillJSON(str);
                        handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private ListView.OnItemClickListener itemListener=new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SecondKillEntity se=(SecondKillEntity)parent.getItemAtPosition(position);
            if(Data.getIfLogin()==false) {
                Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SecondKillActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            Date date=new Date();
            if(date.getTime()>=se.getTime().getTime()) {
                Intent intent = new Intent(SecondKillActivity.this, SecKillPlaceOrderActivity.class);
                intent.putExtra("secondKill", se);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "尚未开始!!", Toast.LENGTH_LONG).show();
            }

        }
    };
}
