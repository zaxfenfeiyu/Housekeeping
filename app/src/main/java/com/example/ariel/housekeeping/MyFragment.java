package com.example.ariel.housekeeping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ariel on 2016/4/14.
 */
public class MyFragment extends Fragment {
    private Button loginBtn;
    private Button perfectInformation;
//    private Button  myCollection;
    private Button moreSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_my, null);


        loginBtn = (Button)view.findViewById(R.id.btn_login);
        perfectInformation=(Button)view.findViewById(R.id.btn_perfect_information) ;
//        myCollection=(Button)view.findViewById(R.id.btn_my_collection) ;
        moreSetting=(Button)view.findViewById(R.id.btn_more_setting) ;
        TextView username=(TextView)view.findViewById(R.id.textView2);

    if(Data.getUsername()!="")
        loginBtn.setVisibility(View.INVISIBLE);
        //设置监听
        //跳转到完善个人信息页面
        perfectInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Data.getIfLogin()==false)
                {
                    Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(getActivity(),InformationActivity.class);
                startActivity(intent);
            }
        });

        //跳转到我的收藏页面
//        myCollection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        //跳转到更多设置页面
        moreSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Data.getIfLogin()==false)
                {
                    Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(getActivity(),MoreSettingActivity.class);
                startActivity(intent);
            }
        });

        username.setText(username.getText()+Data.getUsername());
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里使用getActivity
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}


