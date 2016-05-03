package com.example.ariel.housekeeping;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ariel.housekeeping.entity.TotalOrder;

import java.util.List;

/**
 * Created by ariel on 2016/4/27.
 */
public class AllOrderAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<TotalOrder> list;
    private TextView nameText;
    private TextView stscText;
    private TextView stateText;
    private TextView timeText;
    public AllOrderAdapter(Context c, List<TotalOrder> list) {
        this.list=list;
        inflater = LayoutInflater.from(c) ;
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
        convertView=inflater.inflate(R.layout.order_item,null);
        TotalOrder to=(TotalOrder) getItem(position);
        nameText=(TextView)convertView.findViewById(R.id.text_provider_name);
        stscText=(TextView)convertView.findViewById(R.id.text_st_sc);
        stateText=(TextView)convertView.findViewById(R.id.text_order_state);
        timeText=(TextView)convertView.findViewById(R.id.text_time) ;
        nameText.setText(to.getPro_name());
        stscText.setText(to.getSt_sc_name());
        stateText.setText(to.getState());
        timeText.setText(to.getTime());
        return convertView;
    }
}
