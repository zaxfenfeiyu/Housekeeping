package com.example.ariel.housekeeping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ariel on 2016/4/14.
 */
public class MyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_my, null);
        Button LoginBtn = (Button)view.findViewById(R.id.button9);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里使用getActivity
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
 /*   @Override
    public void onStart() {
        super.onStart();


    }*/
    public void OnClick(View v)
    {

        switch (v.getId())
        {
            case R.id.button9:
                Toast.makeText(getActivity(), "1", Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                intent.setClass(getActivity(),LoginActivity.class);
                startActivity(intent);
        }
    }
}


