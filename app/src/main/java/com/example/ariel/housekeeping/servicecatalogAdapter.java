package com.example.ariel.housekeeping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ariel.housekeeping.entity.ServicecatalogEntity;

import java.util.List;

/**
 * Created by ariel on 2016/4/25.
 */
public class ServicecatalogAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ServicecatalogEntity> list;


    public ServicecatalogAdapter(Context c, List<ServicecatalogEntity>list) {
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
        convertView=inflater.inflate(R.layout.catalog_item,null);
        ServicecatalogEntity pe=(ServicecatalogEntity)getItem(position);
        TextView name=(TextView)convertView.findViewById(R.id.catalogName);
        TextView price=(TextView)convertView.findViewById(R.id.price);
        name.setText(pe.getName());
        price.setText(price.getText().toString()+pe.getPrice());
        ImageView icon=(ImageView)convertView.findViewById(R.id.picture);
        icon.setImageResource(R.drawable.spa_black_24dp);

        return convertView;
    }

}

