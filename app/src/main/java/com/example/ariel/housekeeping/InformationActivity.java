package com.example.ariel.housekeeping;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by ariel on 2016/4/22.
 */
public class InformationActivity extends Activity {
    private ImageButton returnBtn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_imformation);
        returnBtn=(ImageButton)findViewById(R.id.btn_infor_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
