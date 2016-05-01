package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.ResidentEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DBHelper;

/**
 * Created by ariel on 2016/4/14.
 */
public class LoginActivity  extends Activity {
   // private static String urlPath="http://192.168.2.105:8080/HouseKeeping/login.action";
   private static String urlPath="http://192.168.134.1:8080/HouseKeeping/login.action";

    private String NetResult="";
    private List<ResidentEntity> list;
    private EditText usernameText;
    private EditText passwordText;
    private Button LoginBtn;
    private Button registerBtn;
    private  String result="test";
    private ProgressDialog progressDialog;
    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if(result.equals("failed"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_LONG).show();
                    }
                    else if(result!=null) {
                        ResidentEntity residentEntity=list.get(0);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_LONG).show();
                        //登录成功后将信息添加到本地数据库
                        DBHelper dbhelper=new DBHelper(LoginActivity.this);
                        SQLiteDatabase db=dbhelper.getWritableDatabase();
                        db.execSQL("INSERT INTO resident VALUES (?, ?, ?, ?, ?, ?, ?)",
                                new Object[]{null,usernameText.getText(), passwordText.getText(),residentEntity.getRealname(),residentEntity.getAddress(),null,residentEntity.getPhone()});
                        //设置全局变量
                        Data.setRe_id(String.valueOf(residentEntity.getId()));
                        Data.setUsername(usernameText.getText().toString());
                        Data.setAddress(residentEntity.getAddress());
                        Data.setIfLogin(true);
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"连接服务器失败！",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = (EditText) findViewById(R.id.editText);
        passwordText = (EditText) findViewById(R.id.editText2);
        LoginBtn = (Button) findViewById(R.id.button1);
        LoginBtn.setOnClickListener(BtnListener);
        registerBtn=(Button)findViewById(R.id.btn_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
       // new Thread(networkTask).start();

    }

    /*Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作

        }
    };*/

    private Button.OnClickListener BtnListener=new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                progressDialog = ProgressDialog.show(LoginActivity.this,"","正在登录中，请稍后");
                progressDialog.setCancelable(true);
                Map<String, String> params = new HashMap<String, String>();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String username=usernameText.getText().toString();
                            String password=passwordText.getText().toString();
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("account", username);
                            map.put("password",password);
                            InputStream inptStream =RequestService.postRequest(urlPath, map);
                            result =RequestService.dealResponseResult(inptStream);
                            if(!result.equals("failed")){
                                list=RequestService.residentJSON(result);
                            }
                            handler2.sendEmptyMessage(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        };


    }


