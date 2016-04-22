package com.example.ariel.housekeeping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ariel.housekeeping.R;
import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.util.List;

/**
 * Created by ariel on 2016/4/14.
 */
public class ServiceFragment extends Fragment {

    private ListView listView;
    private List<ProviderEntity> list;

    public void setList(List<ProviderEntity> list) {
        this.list = list;
    }

    private ProviderCompanyAdapter pca=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_host, null);
        Bundle data = getArguments();
//        list=(List<ProviderEntity>)data.getSerializable("list");
        Log.e("provider", "provider="+ list.get(1).getName());
        listView=(ListView)view.findViewById(R.id.providerListView);
        pca=new ProviderCompanyAdapter(getActivity(),list);
        listView.setAdapter(pca);

        return view;
    }

}

