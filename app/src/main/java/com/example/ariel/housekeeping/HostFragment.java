package com.example.ariel.housekeeping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ariel on 2016/4/14.
 */
public class HostFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private TextView btn_muying;
    private TextView btn_zhongdian;
    private TextView btn_baomu;
    private TextView btn_kanhu;
    private TextView btn_jiaoyu;
    private TextView btn_qingjie;
    private TextView btn_weixiu;
    private TextView btn_banjia;
    private ViewPager viewPager;
    private LinearLayout vipLayout;
    private Button btn_recommend;
    private int currentItem; // 当前页面
    private int oldPosition = 0;// 记录上一次点的位置
    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;
    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;
    /**
     * 图片资源id
     */
    private int[] imgIdArray ;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_host, null);
        btn_muying = (TextView) view.findViewById(R.id.muying);
        btn_muying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", btn_muying.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_zhongdian = (TextView) view.findViewById(R.id.zhongdian);
        btn_zhongdian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", btn_zhongdian.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_baomu = (TextView) view.findViewById(R.id.baomu);
        btn_baomu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", btn_baomu.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_kanhu = (TextView) view.findViewById(R.id.kanhu);
        btn_kanhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", btn_kanhu.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_jiaoyu = (TextView) view.findViewById(R.id.jiaoyu);
        btn_jiaoyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", btn_jiaoyu.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_qingjie = (TextView) view.findViewById(R.id.qingjie);
        btn_qingjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", btn_qingjie.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_weixiu = (TextView) view.findViewById(R.id.weixiu);
        btn_weixiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", btn_weixiu.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_banjia = (TextView) view.findViewById(R.id.banjia);
        btn_banjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", btn_banjia.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        vipLayout=(LinearLayout)view.findViewById(R.id.vipService);
        vipLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),PlaceVIPOrderActivity.class);
                startActivity(intent);
            }
        });
        btn_recommend=(Button)view.findViewById(R.id.btn_recommend);
        btn_recommend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),RecommendActivity.class);
                startActivity(intent);
            }
        });

        ViewGroup group = (ViewGroup) view.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //载入图片资源ID
        imgIdArray = new int[]{R.drawable.mainpic1, R.drawable.pic1, R.drawable.mainpic2, R.drawable.mainpic3};
        //将点点加入到ViewGroup中
        tips = new ImageView[imgIdArray.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            group.addView(imageView, layoutParams);
        }

        //将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(getContext());
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }
        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((mImageViews.length) * 100);

        return view;


    }

    public class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }



    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setImageBackground(arg0 % mImageViews.length);
    }

    /**
     * 设置选中的tip的背景
     * @param selectItems
     */
    private void setImageBackground(int selectItems){
        for(int i=0; i<tips.length; i++){
            if(i == selectItems){
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }
}