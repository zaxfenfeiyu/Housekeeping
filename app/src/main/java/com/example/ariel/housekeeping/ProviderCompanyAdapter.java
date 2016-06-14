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
    private List<ProviderEntity> list;
    private TextView name;
    private TextView rank;

    public ProviderCompanyAdapter(Context c, List<ProviderEntity> list) {
        this.list = list;
        inflater = LayoutInflater.from(c);
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
        convertView = inflater.inflate(R.layout.provider_item, null);
        ProviderEntity pe = (ProviderEntity) getItem(position);
        name = (TextView) convertView.findViewById(R.id.providerName);
        rank = (TextView) convertView.findViewById(R.id.providerRank);
        name.setText(pe.getName());
        rank.setText(String.valueOf(pe.getRank()));
        return convertView;
    }
}
