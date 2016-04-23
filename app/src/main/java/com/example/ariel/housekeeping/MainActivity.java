package com.example.ariel.housekeeping;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.housekeeping.entity.ProviderEntity;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private LayoutInflater mLayoutInflater;
    private List<ProviderEntity> providerEntities;
    private String urlPath = "http://115.200.23.167:8080/HouseKeeping/getAll.action";
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    Bundle bundle = new Bundle();

                    Log.e("provider", "provider=" + providerEntities.get(0).getName());
                    bundle.putSerializable("list", (Serializable) providerEntities);

                    break;
            }
        }
    };

    /**
     * Fragment数组界面
     */
    private Class mFragmentArray[] = {HostFragment.class, OrderFragment.class,
            MyFragment.class};
    /**
     * 存放图片数组
     */
    private int mImageArray[] = {R.drawable.home2,
             R.drawable.order2,
            R.drawable.my2};
    /**
     * 选修卡文字
     */
    private String mTextArray[] = {"首页", "订单", "我"};


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容

            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
        mTabHost.getTabWidget().setDividerDrawable(null);

    }

    /**
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setTextSize(15);
        textView.setText(mTextArray[index]);
        return view;
    }

    public void getProviders() {
                Toast.makeText(getApplicationContext(), "正在",
                                Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                               try {
                                        providerEntities = RequestService.getJSONData(urlPath);
                                        handler.sendEmptyMessage(0);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                            }
                    }).start();
            }
}

