package com.example.ariel.housekeeping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.util.List;

/**
 * Created by ariel on 2016/4/19.
 */
public class ProviderCompanyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ProviderEntity>list;

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
        TextView name=(TextView)convertView.findViewById(R.id.providerName);
        TextView rank=(TextView)convertView.findViewById(R.id.providerRank);
        name.setText(pe.getName());
        rank.setText(""+pe.getRank());
        return convertView;
    }

}
