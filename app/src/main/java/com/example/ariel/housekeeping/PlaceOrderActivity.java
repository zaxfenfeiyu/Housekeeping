package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by disagree on 2016/4/23.
 */
public class PlaceOrderActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_yuesao);

        //根据用户选择的按钮更改该界面的标题
        TextView textView1=(TextView)findViewById(R.id.textView1);
        Intent intent=new Intent();
        Bundle bundle=intent.getExtras();
        textView1.setText(bundle.getString("type"));


        Button ChooseTypeBtn=(Button)findViewById(R.id.button2);
        ChooseTypeBtn.setOnClickListener(BtnListener);
    }
    private Button.OnClickListener BtnListener=new Button.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.button2:
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlaceOrderActivity.this);
                    builder.setIcon(R.drawable.home2);
                    builder.setTitle("选择一个家政分类");

                    //从数据库中寻找用户所选择的子分类

                    //    指定下拉列表的显示数据
                    final String[] catalog = {"广州", "上海", "北京", "香港", "澳门"};
                    //    设置一个下拉的列表选择项
                    builder.setItems(catalog, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(PlaceOrderActivity.this, "选择的分类为：" + catalog[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
            }
        }
    };
}
