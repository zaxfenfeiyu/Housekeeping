package com.example.ariel.housekeeping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.tools.network.HttpConnection;

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

/**
 * Created by ariel on 2016/4/14.
 */
public class LoginActivity  extends Activity {
   // private static String urlPath="http://115.200.18.5:8080/HouseKeeping/login.action";
   private static String urlPath="http://192.168.47.1:8080/HouseKeeping/login.action";

    private String NetResult="";
    private EditText usernameText;
    private EditText passwordText;
    private Button LoginBtn;
    private  String result="1";
    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:


                    if(result.equals("success")) {
                        Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_LONG).show();

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

                Map<String, String> params = new HashMap<String, String>();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String username=usernameText.getText().toString();
                            String password=passwordText.getText().toString();
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("username", username);
                            map.put("password",password);
                            result=submitPostData(map,"utf-8");
                            handler2.sendEmptyMessage(0);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        };

    public static String submitPostData(Map<String, String> params, String encode) {

        byte[] data = getRequestData(params, encode).toString().getBytes();     //获得请求体
        try {
            URL url=new URL(urlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);          //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");          //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
                 StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
                try {
                        for(Map.Entry<String, String> entry : params.entrySet()) {
                              stringBuffer.append(entry.getKey())
                                             .append("=")
                                             .append(URLEncoder.encode(entry.getValue(), encode))
                                             .append("&");
                             }
                         stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 return stringBuffer;
             }


    public static String dealResponseResult(InputStream inputStream) {
                 String resultData = null;      //存储处理结果
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                 int len = 0;
                 try {
                         while((len = inputStream.read(data)) != -1) {
                                byteArrayOutputStream.write(data, 0, len);
                             }
                   } catch (IOException e) {
                         e.printStackTrace();
                     }
                 resultData = new String(byteArrayOutputStream.toByteArray());
                 return resultData;
             }
    }


