package com.example.ariel.housekeeping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.ServicecatalogEntity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2016/4/23.
 */
public class PlaceOrderActivity extends Activity {
    TextView title,TotalMoney;
    Button ChooseTypeBtn;
    String urlPath="http://192.168.47.1:8080/HouseKeeping/getSCbyST.action";
    String [] catalog={};
    double []money={};
    private List<ServicecatalogEntity> sclist;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -12:
                    int i=0;
                    while(!sclist.isEmpty())
                    {
                        catalog[i]=sclist.get(i).getName();
                        money[i]=sclist.get(i).getPrice();
                        i+=1;
                    }
            }
        }
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_muying);

        //根据用户选择的按钮更改该界面的标题
        title=(TextView)findViewById(R.id.textView1);
        Intent intent=new Intent();
        Bundle bundle=intent.getExtras();
        title.setText(bundle.getString("type"));

        TotalMoney=(TextView)findViewById(R.id.textView8) ;
        ChooseTypeBtn=(Button)findViewById(R.id.button2);
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
//....
                    //从数据库中寻找用户所选择的子分类
                    Map<String, String> params = new HashMap<String, String>();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String st=title.getText().toString();
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("st", st);
                                //list=RequestService.postRequest(urlPath,map);
                                InputStream inptStream = RequestService.postRequest(urlPath, map);
                                String str=RequestService.dealResponseResult(inptStream);
                                sclist=RequestService.servicecatalogJSON(str);
                                handler.sendEmptyMessage(-12);
                               // handler2.sendEmptyMessage(0);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    //    指定下拉列表的显示数据
                    //final String[] catalog = {"广州", "上海", "北京", "香港", "澳门"};
                    //    设置一个下拉的列表选择项
                    builder.setItems(catalog, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(PlaceOrderActivity.this, "选择的分类为：" + catalog[which], Toast.LENGTH_SHORT).show();
                            ChooseTypeBtn.setText(catalog[which]);
                            TotalMoney.setText(TotalMoney.getText().toString()+String.valueOf(money[which]));
                        }
                    });
                    builder.show();
            }
        }
    };
}
