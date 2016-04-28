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
    private Button completed;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_order, null);
        allOrder=(Button)view.findViewById(R.id.btn_all_order);
        waitTake=(Button)view.findViewById(R.id.btn_wait_take);
        waitConfirm=(Button)view.findViewById(R.id.btn_wait_confirm);
        waitEvaluate=(Button)view.findViewById(R.id.btn_wait_evaluate);
        completed=(Button)view.findViewById(R.id.btn_completed);
        allOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),AllOrderActivity.class);
                intent.putExtra("title","全部订单");
                intent.putExtra("tag","getAllOrder.action");
                startActivity(intent);
            }
        });
        waitTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),AllOrderActivity.class);
                intent.putExtra("title","待接单订单");
                intent.putExtra("tag","getWaitTakeOrder.action");
                startActivity(intent);
            }
        });
        waitConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),AllOrderActivity.class);
                intent.putExtra("title","待确认订单");
                intent.putExtra("tag","getWaitConfirmOrder.action");
                startActivity(intent);
            }
        });
        waitEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),AllOrderActivity.class);
                intent.putExtra("title","待评价订单");
                intent.putExtra("tag","getWaitRemarkOrder.action");
                startActivity(intent);
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),AllOrderActivity.class);
                intent.putExtra("title","已完成订单");
                intent.putExtra("tag","getCompletedOrder.action");
                startActivity(intent);
            }
        });
        return view;
    }

}
