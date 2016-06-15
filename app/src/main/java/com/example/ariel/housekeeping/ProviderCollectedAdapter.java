package com.example.ariel.housekeeping;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.util.List;

/**
 * Created by disagree on 2016/6/14.
 */
public class ProviderCollectedAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ProviderEntity> list;
    private TextView name;
    private TextView rank;

    public ProviderCollectedAdapter(Context c, List<ProviderEntity> list) {
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
        convertView = inflater.inflate(R.layout.provider_collected, null);
        ProviderEntity pe = (ProviderEntity) getItem(position);
        name = (TextView) convertView.findViewById(R.id.providerName);
        rank = (TextView) convertView.findViewById(R.id.providerRank);
        name.setText(pe.getName());
        rank.setText(String.valueOf(pe.getRank()));
        return convertView;
    }
}
