package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.OrderDetail;
import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResidentCollectionActivity  extends Activity {
    private ListView listView;
    private TextView noData;
    private List<ProviderEntity> list;
    private ProgressDialog progressDialog;
    private ProviderCollectedAdapter pca;
    String urlPath1 = "http://"+Data.ip+":8080/HouseKeeping/getCollectionByReId.action";
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progressDialog.dismiss();
                    if (list.isEmpty()) {

                    } else {
                        pca = new ProviderCollectedAdapter(getApplicationContext(), list);
                        listView.setAdapter(pca);
                        listView.setOnItemClickListener(itemListener);
                    }

                    break;

                case -1:
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_provider);
        noData=(TextView)findViewById(R.id.text_empty_provider) ;
        listView=(ListView)findViewById(R.id.providerListView) ;
        listView.setEmptyView(noData);


        getCollectedProvider();
    }

    public void getCollectedProvider()
    {
        progressDialog = ProgressDialog.show(ResidentCollectionActivity.this, "", "正在获取，请稍后");
        progressDialog.setCancelable(true);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("re_id", Data.getRe_id());
                    InputStream inputStream = RequestService.postRequest(urlPath1, map);
                    if (inputStream == null) {
                        handler.sendEmptyMessage(-1);
                    } else {
                        String str = RequestService.dealResponseResult(inputStream);
                        list = RequestService.providerJSON(str);
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
            Intent intent=new Intent(ResidentCollectionActivity.this,RecProviderDetailActivity.class);
            intent.putExtra("provider",pe);
            startActivity(intent);
        }
    };
}
