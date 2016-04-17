package com.example.ariel.housekeeping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by ariel on 2016/4/14.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText account;
    private EditText password;
    private EditText repassword;
    private EditText verifyCode;
    private Button verifyCodeBtn;
    private Button submitBtn;
    private String APPKEY = "11b16f73997aa";
    private String APPSECRETE = "301a2053d8243101c649a8e94ce1f414";
    private String urlPath = "http://115.200.9.158:8080/HouseKeeping/register.action";
    private String accountStr;
    private String passwordStr;
    private String repasswordStr;
    private String verifyCodeStr;
    private int time = 60;
    private boolean ifcode=false;
    private String result = "";
    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        account = (EditText) findViewById(R.id.register_input_account_edit);
        password = (EditText) findViewById(R.id.register_input_password_edit);
        repassword = (EditText) findViewById(R.id.register_input_repassword_edit);
        verifyCode = (EditText) findViewById(R.id.register_input_code_edit);
        verifyCodeBtn = (Button) findViewById(R.id.register_request_code_btn);
        verifyCodeBtn.setOnClickListener(this);
        submitBtn = (Button) findViewById(R.id.commit_btn);
        submitBtn.setOnClickListener(this);

        // 启动短信验证sdk
        SMSSDK.initSDK(this, APPKEY, APPSECRETE);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发送验证码
            case R.id.register_request_code_btn:
                //判断手机号码格式是否正确
                if (!judgePhoneNums(accountStr)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", accountStr); // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                verifyCodeBtn.setClickable(false);
                verifyCodeBtn.setText("重新发送(" + time-- + ")");
                //重新发送倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 60; i > 0; i--, time--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
            //提交注册
            case R.id.commit_btn:
                if( validate()) {
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                verifyCodeBtn.setText("重新发送(" + time + ")");
            } else if (msg.what == -8) {
                verifyCodeBtn.setText("获取验证码");
                verifyCodeBtn.setClickable(true);
                time = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        ifcode=true;
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };

    //判断手机号码格式
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码格式不正确！", Toast.LENGTH_SHORT).show();
        return false;
    }

    public  boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public  boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
    //验证用户填写是否正确
    public boolean validate() {
        accountStr=account.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        repasswordStr = repassword.getText().toString().trim();
        verifyCodeStr=verifyCode.getText().toString().trim();
        Toast.makeText(getApplicationContext(), "accountStr="+accountStr+ " passwordStr="+passwordStr+" repasswordStr="+repasswordStr+" verifyCodeStr="+verifyCodeStr, Toast.LENGTH_SHORT).show();
        if(!judgePhoneNums(accountStr)){
            return false;
        }
        if (passwordStr.length() < 6 || passwordStr.length() > 16) {
            Toast.makeText(getApplicationContext(), "密码应为6-16位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!repasswordStr.equals(passwordStr)){
            Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(verifyCodeStr.equals("")){
//            Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        SMSSDK.submitVerificationCode("86", accountStr, verifyCode
//                .getText().toString());
//        if(!ifcode){
//            Toast.makeText(getApplicationContext(), "验证码不正确", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if(!Register()){
            return false;
        }
        return true;
    }
//该手机号是否已经注册
    public boolean Register(){
        Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("username", accountStr);
                    map.put("password",passwordStr);
                    result = RequestService.postRequest(urlPath, map);
                    handler2.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        if(result.equals("existed")){
            Toast.makeText(getApplicationContext(), "该手机已注册", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(result.equals("success")){
            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(result.equals("fail")){
            Toast.makeText(getApplicationContext(), "不知名错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

//    @Override
//    protected void onDestroy() {
//        SMSSDK.unregisterAllEventHandler();
//        super.onDestroy();
//    }
}
