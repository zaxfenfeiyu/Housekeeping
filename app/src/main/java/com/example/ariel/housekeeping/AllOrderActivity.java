package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.ProviderEntity;
import com.example.ariel.housekeeping.entity.TotalOrder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ariel on 2016/4/22.
 */
public class AllOrderActivity extends Activity {
    private ImageButton returnBtn;
    private ListView listView;
    private TextView noData;
    private TextView titleText;
    private String title;
    private String action;
    private List<TotalOrder> list;
    private int order_id;
    private ProgressDialog progressDialog;
    private AllOrderAdapter aoa;
    private String urlPath="http://192.168.134.1:8080/HouseKeeping/";
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progressDialog.dismiss();
                    if (list.isEmpty()) {

                    } else {
                        aoa = new AllOrderAdapter(getApplicationContext(), list);
                        listView.setAdapter(aoa);
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
        setContentView(R.layout.activity_all_order);
        noData=(TextView)findViewById(R.id.text_no_data) ;
        titleText=(TextView)findViewById(R.id.text_title) ;
        returnBtn = (ImageButton) findViewById(R.id.btn_all_order_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.order_listView);
        listView.setEmptyView(noData);
        Intent intent=getIntent();
        action=intent.getStringExtra("tag");
        title=intent.getStringExtra("title");
        titleText.setText(title);
        //
        getAllOrder();
    }

    public void getAllOrder() {
        progressDialog = ProgressDialog.show(AllOrderActivity.this, "", "正在获取，请稍后");
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("re_account", Data.getUsername());
                    InputStream inputStream = RequestService.postRequest(urlPath+action, map);
                    if (inputStream == null) {
                        handler.sendEmptyMessage(-1);
                    } else {
                        String str = RequestService.dealResponseResult(inputStream);
                        list = RequestService.totalOrderJSON(str);
                        handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private ListView.OnItemClickListener itemListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TotalOrder to = (TotalOrder) parent.getItemAtPosition(position);
            order_id = to.getId();
            Intent intent = new Intent(AllOrderActivity.this, OrderDetailActivity.class);
            intent.putExtra("order_id", order_id);
            startActivity(intent);
        }
    };

}
