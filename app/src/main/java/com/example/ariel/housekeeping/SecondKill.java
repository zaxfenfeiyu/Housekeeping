package com.example.ariel.housekeeping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondKill extends AppCompatActivity {
    Button SecondKillBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_kill);
        SecondKillBtn=(Button)findViewById(R.id.SecondKill);
        SecondKillBtn.setClickable(false);
        SecondKillBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),HostFragment.class);//修改
                startActivity(intent);
            }
        });

    }

}
