package com.example.ariel.housekeeping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by ariel on 2016/4/14.
 */
public class HostFragment extends Fragment {
    Button btn_muying;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_host, null);
        btn_muying = (Button)view.findViewById(R.id.muying);
        btn_muying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PlaceOrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type",btn_muying.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }


}