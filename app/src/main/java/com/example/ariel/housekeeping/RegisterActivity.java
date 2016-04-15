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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by ariel on 2016/4/14.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText account;
    private EditText password;
    private EditText repassword;
    private EditText email;
    private EditText verifyCode;
    private Button verifyCodeBtn;
    private Button submitBtn;
    private String APPKEY = "11b16f73997aa";
    private String APPSECRETE = "301a2053d8243101c649a8e94ce1f414";
    private int time=60;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        account = (EditText) findViewById(R.id.register_input_account_edit);
        password = (EditText) findViewById(R.id.register_input_password_edit);
        repassword = (EditText) findViewById(R.id.register_input_repassword_edit);
        email = (EditText) findViewById(R.id.register_input_email_edit);
        verifyCode = (EditText) findViewById(R.id.register_input_code_edit);
        verifyCodeBtn = (Button) findViewById(R.id.register_request_code_btn);
        verifyCodeBtn .setOnClickListener(this);
        submitBtn = (Button) findViewById(R.id.commit_btn);
        submitBtn .setOnClickListener(this);

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
        String phoneNums = account.getText().toString().trim();
        switch (v.getId()) {
            case R.id.register_request_code_btn:
                //判断手机号码格式是否正确
                if (!judgePhoneNums(phoneNums)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums); // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                verifyCodeBtn.setClickable(false);
                verifyCodeBtn.setText("重新发送(" + time-- + ")");
                //重新发送倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 60; i > 0; i--,time--) {
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

            case R.id.commit_btn:
                SMSSDK.submitVerificationCode("86", phoneNums, verifyCode
                        .getText().toString());
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
                                Intent intent=new Intent();
                                intent.setClass(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);

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

    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
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
    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
