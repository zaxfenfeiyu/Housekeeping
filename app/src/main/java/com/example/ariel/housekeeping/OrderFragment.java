package com.example.ariel.housekeeping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ariel on 2016/4/14.
 */
public class OrderFragment extends Fragment {
    private Button allOrder;
    private Button waitTake;
    private Button waitConfirm;
    private Button waitEvaluate;
    private Button register;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_order, null);
        allOrder=(Button)view.findViewById(R.id.btn_all_order);
        waitTake=(Button)view.findViewById(R.id.btn_wait_take);
        waitConfirm=(Button)view.findViewById(R.id.btn_wait_confirm);
        waitEvaluate=(Button)view.findViewById(R.id.btn_wait_evaluate);
        register=(Button)view.findViewById(R.id.btn_reg);
        allOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),AllOrderActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
