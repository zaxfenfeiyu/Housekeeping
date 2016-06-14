package com.example.ariel.housekeeping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ariel.housekeeping.entity.ProviderEntity;

public class RecProviderDetailActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView introductionText;
    private TextView phoneText;
    private TextView rankText;
    private TextView serviceText;
    private ImageButton returnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_detail);

        nameText=(TextView) findViewById(R.id.text_rec_name);
        introductionText=(TextView) findViewById(R.id.text_rec_introduction);
        phoneText=(TextView) findViewById(R.id.text_rec_phone);
        rankText=(TextView) findViewById(R.id.text_rec_rank);
        serviceText=(TextView) findViewById(R.id.text_rec_service);

        returnBtn = (ImageButton) findViewById(R.id.rec_det_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent =getIntent();
        ProviderEntity pe=(ProviderEntity)intent.getSerializableExtra("provider");

        nameText.setText(pe.getName());
        introductionText.setText(pe.getIntroduction());
        phoneText.setText(pe.getPhone());
        rankText.setText(String.valueOf(pe.getRank()));
        serviceText.setText(pe.getService());
    }
}
