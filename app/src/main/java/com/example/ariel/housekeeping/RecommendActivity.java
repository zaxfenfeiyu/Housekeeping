package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendActivity extends Activity {
    private ImageButton returnBtn;
    private ListView listView;
    private TextView noData;
    private ProgressDialog progressDialog;
    private List<ProviderEntity> plist;
    private ProviderCompanyAdapter pca = null;
    private String urlPath="http://"+Data.ip+":8080/HouseKeeping/getRecommendProviders.action";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progressDialog.dismiss();
                        pca = new ProviderCompanyAdapter(getApplicationContext(), plist);
                        listView.setAdapter(pca);
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
        setContentView(R.layout.activity_recommend);
        noData=(TextView)findViewById(R.id.text_rec_empty) ;
        returnBtn = (ImageButton) findViewById(R.id.rec_returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.rec_listView);
        listView.setEmptyView(noData);
        getRecommendProvider();
    }

    public void getRecommendProvider() {
        progressDialog = ProgressDialog.show(RecommendActivity.this, "", "正在获取，请稍后");
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
                        plist=RequestService.providerJSON(str);
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
            ProviderEntity pe=(ProviderEntity)parent.getItemAtPosition(position);
            Intent intent=new Intent(RecommendActivity.this,RecProviderDetailActivity.class);
            intent.putExtra("provider",pe);
            startActivity(intent);
        }
    };


}
