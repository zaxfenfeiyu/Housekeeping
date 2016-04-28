package com.example.ariel.housekeeping;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.ProviderEntity;
import com.example.ariel.housekeeping.entity.ServicecatalogEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Exchanger;

/**
 * Created by ariel on 2016/4/19.
 */
public class ProviderCompanyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ProviderEntity>list;
    private TextView name;
    private TextView rank;
    private ImageView imageView;
    private Bitmap bitmap;
    private String pictureUrl;
    private  Drawable drawable;
    String urlPath1 = "http://192.168.134.1:8080/HouseKeeping/permit/";
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

//                    Log.e("t", "handleMessage: " +bitmap.getHeight());
                   imageView.setImageDrawable(drawable);
                    //imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };
    public ProviderCompanyAdapter(Context c, List<ProviderEntity>list) {
        this.list=list;
        inflater =LayoutInflater.from(c) ;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.provider_item,null);
        ProviderEntity pe=(ProviderEntity)getItem(position);
        name=(TextView)convertView.findViewById(R.id.providerName);
        rank=(TextView)convertView.findViewById(R.id.providerRank);
        imageView=(ImageView) convertView.findViewById(R.id.picture);
        name.setText(pe.getName());

        pictureUrl=pe.getPicturepath().toString().trim();
        rank.setText(pictureUrl+pe.getRank());
        if(!pictureUrl.equals("")) {
            getPicture();


        }
        else{
            imageView.setBackgroundResource(R.drawable.pic2);
        }
        return convertView;
    }
        public void getPicture()
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                         //bitmap=RequestService.getUrlImage(urlPath1+pictureUrl);
                        //  drawable = Drawable.createFromStream(new URL(urlPath1+pictureUrl).openStream(), "image.jpg");
                        String testUrl="http://imgsrc.baidu.com/forum/w%3D580/sign=64d9331fcb95d143da76e42b43f18296/ceccafef76094b368ad1c4f1a0cc7cd98c109dc9.jpg";
                        drawable = Drawable.createFromStream(new URL(testUrl).openStream(), "image.jpg");
                        handler.sendEmptyMessage(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
}
