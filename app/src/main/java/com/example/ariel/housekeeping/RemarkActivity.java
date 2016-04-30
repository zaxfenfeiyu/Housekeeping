package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ariel on 2016/4/28.
 */
public class RemarkActivity extends Activity {
    private RatingBar ratingBar;
    private TextView proNameText;
    private EditText remarkEdit;
    private String remark;
    private Button submit;
    private int order_id;
    private String proName;
    private float rank = 0;
    private String result;
    //private String urlPath = "http://115.200.19.98:8080/HouseKeeping/remarkOrder.action";
    private String urlPath = "http://192.168.2.105:8080/HouseKeeping/remarkOrder.action";
    ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progressDialog.dismiss();
                    if(result.equals("succeed")) {
                        Toast.makeText(getApplicationContext(), "感谢评价", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RemarkActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
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
        setContentView(R.layout.activity_remark);
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);
        proName=intent.getStringExtra("pro_name");
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBarChangeListener());
        proNameText = (TextView) findViewById(R.id.tetx_provider_name);
        proNameText.setText(proName);
        remarkEdit = (EditText) findViewById(R.id.edit_remark);
        submit = (Button) findViewById(R.id.btn_submit_remark);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    submitRemark();
                }
            }
        });
    }

    class RatingBarChangeListener implements RatingBar.OnRatingBarChangeListener {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            Log.i("rating", "当前分数=" + rating);
            rank = rating;
        }
    }

    public boolean validate() {
        remark = remarkEdit.getText().toString().trim();
        if (remark.equals("")) {
            Toast.makeText(RemarkActivity.this, "请填写评价", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void submitRemark() {
        progressDialog = ProgressDialog.show(RemarkActivity.this, "", "正在获取，请稍后");
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("order_id",String.valueOf(order_id) );
                    map.put("rank",String.valueOf(rank) );
                    map.put("remark",remark );
                    InputStream inputStream = RequestService.postRequest(urlPath,map);
                    if (inputStream == null) {
                        handler.sendEmptyMessage(-1);
                    } else {
                        result = RequestService.dealResponseResult(inputStream);
                        handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
