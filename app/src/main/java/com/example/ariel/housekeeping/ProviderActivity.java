package com.example.ariel.housekeeping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.R;
import com.example.ariel.housekeeping.entity.OrderDetail;
import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.util.List;

/**
 * Created by ariel on 2016/4/14.
 */
public class ProviderActivity extends Activity {

    private ListView listView;
    private List<ProviderEntity> list;
    private TextView noData;
    private OrderDetail orderDetail;
    private int pro_id;
    private ProviderCompanyAdapter pca = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        noData=(TextView)findViewById(R.id.text_empty_provider) ;
        listView=(ListView)findViewById(R.id.providerListView) ;
        listView.setEmptyView(noData);
        Intent intent = getIntent();
        list = (List<ProviderEntity>) intent.getSerializableExtra("plist");
        orderDetail = (OrderDetail) intent.getSerializableExtra("orderDetail");
        pca=new ProviderCompanyAdapter(getApplicationContext(),list);
        listView.setAdapter(pca);
        listView.setOnItemClickListener(itemListener);
    }

    private ListView.OnItemClickListener itemListener=new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ProviderEntity pe=(ProviderEntity)parent.getItemAtPosition(position);
            pro_id=pe.getId();
            Intent intent=new Intent(ProviderActivity.this,ProviderDetailActivity.class);
            intent.putExtra("provider",pe);
            intent.putExtra("orderDetail", orderDetail);
            startActivity(intent);
        }
    };

}

