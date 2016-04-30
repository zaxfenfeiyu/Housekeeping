package com.example.ariel.housekeeping;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import db.DBHelper;

public class WelcomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom);
        //查询数据库中的数据以判断用户是否之前登录过了
        DBHelper dbhelper=new DBHelper(WelcomActivity.this);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Cursor cursor=db.query("resident",new String[]{"username"},"id=1",null,null,null,null,null);
        if(cursor.moveToFirst())
        if(!cursor.isNull(0)) {
            Data.setIfLogin(true);
            Data.setUsername(cursor.getString(0));
        }

        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("com.example.ariel.housekeeping", 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setText("Version " + pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent intent = new Intent(WelcomActivity.this,MainActivity.class);
                startActivity(intent);
                WelcomActivity.this.finish();
            }

        }, 2000);
    }
}
