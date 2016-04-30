package com.example.ariel.housekeeping;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import db.DBHelper;

/**
 * Created by ariel on 2016/4/22.
 */
public class MoreSettingActivity extends Activity {
    private ImageButton returnBtn;
    private Button logoutBtn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        returnBtn=(ImageButton)findViewById(R.id.btn_setting_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        logoutBtn=(Button)findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Data.setIfLogin(false);
                Data.setUsername("");
                DBHelper dbhelper=new DBHelper(MoreSettingActivity.this);
                SQLiteDatabase db=dbhelper.getWritableDatabase();
                db.delete("resident",null,null);
                Toast.makeText(MoreSettingActivity.this,"注销成功！",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MoreSettingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
