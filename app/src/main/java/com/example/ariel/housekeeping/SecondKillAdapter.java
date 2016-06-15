package com.example.ariel.housekeeping;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ariel.housekeeping.entity.SecondKillEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ariel on 2016/6/15.
 */
public class SecondKillAdapter  extends BaseAdapter {
    private LayoutInflater inflater;
    private List<SecondKillEntity> list;
    private TextView nameText;
    private TextView dateText;

    public SecondKillAdapter(Context c, List<SecondKillEntity> list) {
        inflater =LayoutInflater.from(c) ;
        this.list = list;
    }

//    @Override
//    public boolean isEnabled(int position) {
//        SecondKillEntity se=(SecondKillEntity)getItem(position);
//        Date date=new Date();
//        if(date.getTime()>=se.getTime().getTime()){
//            return true;
//        }
//      else{
//            return false;
//        }
//    }
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
        convertView=inflater.inflate(R.layout.second_kill_item,null);
        nameText=(TextView) convertView.findViewById(R.id.text_sec_name);
        dateText=(TextView) convertView.findViewById(R.id.text_sec_time);
        SecondKillEntity ske=(SecondKillEntity)getItem(position);
        nameText.setText(ske.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sdf.format(ske.getTime());
        dateText.setText(time);
        return convertView;
    }
}
