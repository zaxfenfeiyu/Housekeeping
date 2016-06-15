package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.ResidentEntity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import db.DBHelper;

/**
 * Created by ariel on 2016/4/22.
 */
public class InformationActivity extends Activity implements View.OnClickListener {
    private EditText realName;
    private EditText address;
    private EditText phoneNum;
    private EditText confirmCode;
    private String realNameStr;
    private String addressStr;
    private String phoneNumStr;
    private String confirmCodeStr;
    private Button confirmCodeBtn;
    private Button commitBtn;
    private String account;
    private ImageButton returnBtn;
    private String APPKEY = "11b16f73997aa";
    private String APPSECRETE = "301a2053d8243101c649a8e94ce1f414";
    //private String urlPath2 = "http://192.168.2.105:8080/HouseKeeping/editInfor.action";
   // private String urlPath1 = "http://192.168.2.105:8080/HouseKeeping/getInfor.action";
    private String urlPath2 = "http://"+Data.ip+":8080/HouseKeeping/editInfor.action";
    private String urlPath1 = "http://"+Data.ip+":8080/HouseKeeping/getInfor.action";
    private int time = 60;
    private String retrunRes = "";
    private  List<ResidentEntity> relist;
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_imformation);

        //ID赋值
        confirmCode = (EditText) findViewById(R.id.input_confirmCode);
        confirmCodeBtn = (Button) findViewById(R.id.btn_confirmCode);
        phoneNum = (EditText) findViewById(R.id.input_phoneNum);
        realName = (EditText) findViewById(R.id.input_realName);
        address = (EditText) findViewById(R.id.input_personAddress);
        commitBtn=(Button) findViewById(R.id.btn_infor_commit);

        confirmCodeBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);

        //监听返回键
        returnBtn = (ImageButton) findViewById(R.id.btn_infor_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getInfor();
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
            case R.id.btn_confirmCode:
                phoneNumStr = phoneNum.getText().toString().trim();
                //判断手机号码格式是否正确
                if (!judgePhoneNums(phoneNumStr)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNumStr); // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                confirmCodeBtn.setClickable(false);
                confirmCodeBtn.setText("重新发送(" + time-- + ")");
                //重新发送倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = time; i > 0; i--, time--) {
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
            //提交
            case R.id.btn_infor_commit:
                validate();
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                confirmCodeBtn.setText("重新发送(" + time + ")");
            } else if (msg.what == -8) {
                confirmCodeBtn.setText("获取验证码");
                confirmCodeBtn.setClickable(true);
                time = 60;
            } else if (msg.what == -10) {
                progressDialog.dismiss();
                if (retrunRes.equals("existed")) {
                    Toast.makeText(getApplicationContext(), "该手机已注册", Toast.LENGTH_SHORT).show();
                }
                if (retrunRes.equals("succeed")) {
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    finish();

                }
                if (retrunRes.equals("failed")) {
                    Toast.makeText(getApplicationContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                }
            }
            else if (msg.what == -11) {
                realName.setText(relist.get(0).getRealname());
                address.setText(relist.get(0).getAddress());
                phoneNum.setText(relist.get(0).getPhone());
            }
            else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        progressDialog = ProgressDialog.show(InformationActivity.this, "", "正在提交，请稍后");
                        progressDialog.setCancelable(true);
                        perfect();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "操作失败",
                                Toast.LENGTH_SHORT).show();
                        ((Throwable) data).printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "验证码错误",
                            Toast.LENGTH_SHORT).show();
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

    public boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public boolean isMobileNO(String mobileNums) {

        String telRegex = "^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    //该手机号是否已经注册
    public void perfect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("account", account);
                    map.put("realName", realNameStr);
                    map.put("phoneNum", phoneNumStr);
                    map.put("address", addressStr);
                    Data.setAddress(addressStr);
                    //将address存入本地数据库
                    DBHelper dbhelper=new DBHelper(InformationActivity.this);
                    SQLiteDatabase db=dbhelper.getWritableDatabase();
                    ContentValues cv=new ContentValues();
                    cv.put("address",addressStr);
                    db.update("resident",cv,"id=1",null);

                    InputStream inptStream = RequestService.postRequest(urlPath2, map);
                    retrunRes = RequestService.dealResponseResult(inptStream);
                    handler.sendEmptyMessage(-10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //验证
    public boolean validate() {
        realNameStr = realName.getText().toString().trim();
        phoneNumStr = phoneNum.getText().toString().trim();
        addressStr = address.getText().toString().trim();
        confirmCodeStr = confirmCode.getText().toString().trim();
        if (realNameStr.equals("")) {
            Toast.makeText(this, "姓名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!judgePhoneNums(phoneNumStr)) {
            return false;
        }
        if (addressStr.equals("")) {
            Toast.makeText(this, "地址不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirmCodeStr.equals("")) {
            Toast.makeText(this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        SMSSDK.submitVerificationCode("86", phoneNumStr, confirmCode
                .getText().toString());
        return true;
    }

    public void getInfor() {
        account = Data.getUsername();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("account", account);
                    InputStream inptStream = RequestService.postRequest(urlPath1, map);
                    String str=RequestService.dealResponseResult(inptStream);
                    relist=RequestService.residentJSON(str);
                    handler.sendEmptyMessage(-11);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
