package com.example.ariel.housekeeping;

import android.util.Log;

import com.example.ariel.housekeeping.entity.ProviderEntity;
import com.example.ariel.housekeeping.entity.ResidentEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ariel on 2016/4/17.
 */
public class RequestService {
    public static InputStream postRequest(String urlPath,Map<String,String> map) throws Exception {
        byte[] data = getRequestData(map, "utf-8").toString().getBytes();
        URL url=new URL(urlPath);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setConnectTimeout(3000);        //设置连接超时时间
        httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
        httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
        httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
        httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
        //设置请求体的类型是文本类型
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //设置请求体的长度
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
        //获得输出流，向服务器写入数据
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(data);
        //状态码是不成功
        int response = httpURLConnection.getResponseCode();            //获得服务器的响应码

        if (response == HttpURLConnection.HTTP_OK) {
            InputStream inptStream = httpURLConnection.getInputStream();
            return inptStream;   //处理服务器的响应结果
        }
            return null;
    }

    public static String dealResponseResult(InputStream inputStream) {
                 String resultData = null;      //存储处理结果
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 byte[] data = new byte[1024];
                 int len = 0;
                 try {
                        while((len = inputStream.read(data)) != -1) {
                            Log.e("event", "event=" + data);
                                 byteArrayOutputStream.write(data, 0, len);
                             }
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 resultData = new String(byteArrayOutputStream.toByteArray());
                 return resultData;
             }

    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {            for(Map.Entry<String, String> entry : params.entrySet()) {
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
    public static List<ProviderEntity> getJSONData(String urlPath) throws Exception {
        List<ProviderEntity> list = new ArrayList<ProviderEntity>();
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);        //设置连接超时时间
        conn.setDoInput(true);                  //打开输入流，以便从服务器获取数据
        int response = conn.getResponseCode();            //获得服务器的响应码
        Log.e("conn", "conn="+ response);
        if (response== 200) {
            InputStream in = conn.getInputStream();
            String str=dealResponseResult(in);
        }
        return list;

    }
    /**
     * 将json数据转化成providerEntity
     * @param str
     * @return providerEntity的List
     * @throws Exception
     */
    public static List<ProviderEntity> providerJSON(String str)throws Exception{
        List<ProviderEntity> list = new ArrayList<ProviderEntity>();
        ProviderEntity providerEntity = null;
        JSONArray array = new JSONArray(str);
        int length = array.length();
        for(int i=0;i<length;i++){
            JSONObject object = array.getJSONObject(i);
            providerEntity = new ProviderEntity( object.getString("name"), object.getInt("rank"));
            list.add(providerEntity);
        }
        return list;
    }
    /**
     * 将json数据转化成ResidentEntity
     * @param str
     * @return ResidentEntity的List
     * @throws Exception
     */
    public static List<ResidentEntity> residentJSON(String str)throws Exception{
        List<ResidentEntity> list = new ArrayList<ResidentEntity>();
        ResidentEntity residentEntity = null;
        JSONArray array = new JSONArray(str);
        int length = array.length();
        for(int i=0;i<length;i++){
            JSONObject object = array.getJSONObject(i);
            residentEntity = new ResidentEntity( object.getString("account"),object.getString("realname"),object.getString("address"),object.getString("phone"));
            list.add(residentEntity);
        }
        return list;
    }
}
