package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ariel on 2016/4/14.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText account;
    private EditText password;
    private EditText repassword;
    private Button submitBtn;
    private String urlPath = "http://192.168.134.1:8080/HouseKeeping/register.action";
   // private String urlPath = "http://192.168.155.1:8080/HouseKeeping/register.action";
    private String accountStr;
    private String passwordStr;
    private String repasswordStr;
    private String retrunRes = "";
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        account = (EditText) findViewById(R.id.register_input_account_edit);
        password = (EditText) findViewById(R.id.register_input_password_edit);
        repassword = (EditText) findViewById(R.id.register_input_repassword_edit);
        submitBtn = (Button) findViewById(R.id.commit_btn);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //提交注册
            case R.id.commit_btn:
                validate();
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -10) {
                progressDialog.dismiss();
                if (retrunRes.equals("existed")) {
                    Toast.makeText(getApplicationContext(), "该登记号已注册", Toast.LENGTH_SHORT).show();
                }
                else if (retrunRes.equals("succeed")) {
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if (retrunRes.equals("failed")) {
                    Toast.makeText(getApplicationContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "连接服务器错误", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

    //验证用户填写是否正确
    public boolean validate() {
        accountStr = account.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        repasswordStr = repassword.getText().toString().trim();
        //  Toast.makeText(getApplicationContext(), "accountStr="+accountStr+ " passwordStr="+passwordStr+" repasswordStr="+repasswordStr+" verifyCodeStr="+verifyCodeStr, Toast.LENGTH_SHORT).show();
        if (accountStr.equals("")) {
            Toast.makeText(getApplicationContext(), "登记号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passwordStr.length() < 6 || passwordStr.length() > 16) {
            Toast.makeText(getApplicationContext(), "密码应为6-16位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!repasswordStr.equals(passwordStr)) {
            Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        progressDialog = ProgressDialog.show(RegisterActivity.this,"","正在注册，请稍后");
        progressDialog.setCancelable(true);
        register();
        return true;
    }

    //该注册号是否已经注册
    public void register() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("account",accountStr);
                    map.put("password", passwordStr);
                    InputStream inptStream =RequestService.postRequest(urlPath, map);
                    retrunRes =RequestService.dealResponseResult(inptStream);
                    handler.sendEmptyMessage(-10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
