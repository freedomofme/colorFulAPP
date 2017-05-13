package com.hhxfight.recolorer.Activity.color.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boycy815.pinchimageview.PinchImageView;
import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.config.Url;
import com.hhxfight.recolorer.util.AssetsUtil;
import com.hhxfight.recolorer.util.ImageIoUtil;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.victor.loading.book.BookLoading;
import com.yanzhenjie.album.Album;

/**
 * Created by HHX on 2017/4/6.
 */


public class ColorTransformActivity extends FragmentActivity {

    PinchImageView bg;
    RecyclerView recyclerView;
    BookLoading bookLoading;
    final Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        inflate = LayoutInflater.from(getBaseContext());

        ScrollIndicatorView indicator = (ScrollIndicatorView) findViewById(R.id.siv_features);
        indicator.setScrollBar(new ColorBar(this, Color.RED, 5));
        int selectColorId = R.color.tab_top_text_2;
        int unSelectColorId = R.color.tab_top_text_1;
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(this, selectColorId, unSelectColorId));

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_features);

        viewPager.setOffscreenPageLimit(2);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));


        bg = (PinchImageView) findViewById(R.id.piv_bg);

        Bitmap bitmap = AssetsUtil.getBitmap(this, "greenAni.png");
        bg.setImageBitmap(bitmap);
        bookLoading = (BookLoading) findViewById(R.id.bl_bookloading);
        bookLoading.setVisibility(View.GONE);


        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Album.album(ColorTransformActivity.this)
                        .requestCode(999) // 请求码，返回时onActivityResult()的第一个参数。
//						.toolBarColor(toolbarColor) // Toolbar 颜色，默认蓝色。
//						.statusBarColor(statusBarColor) // StatusBar 颜色，默认蓝色。
//						.navigationBarColor(navigationBarColor) // NavigationBar 颜色，默认黑色，建议使用默认。
                        .title("图库") // 配置title。
                        .selectCount(1) // 最多选择几张图片。
                        .columnCount(2) // 相册展示列数，默认是2列。
                        .camera(true) // 是否有拍照功能。
//						.checkedList() // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                        .start();
            }
        }, 2000);

//        recyclerView = (RecyclerView) findViewById(R.id.rv_color);
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
//        adapter = new MyAdapter(this, getPicUrls());
//        useLinearLayoutManager();
//        useGridLayoutManager();
//      useStaggeredGridLayoutManager();
//        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        myHandler.post(() ->{
                    bg.buildDrawingCache();
                    ImageIoUtil.saveBitmap(Url.APPDIR, "test.png", bg.getDrawingCache());
                    bg.destroyDrawingCache();
                }
        );
    }

    private void useLinearLayoutManager() {
        // 创建线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // 设置显示布局的方向，默认方向是垂直
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void useGridLayoutManager() {
        // 创建网格布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        // 设置显示布局的方向，默认方向是垂直
//        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private String[] getPicUrls() {
        String[] picUrls = new String[]{
                "http://img0.hao123.com/data/1_2d6066fea769896573b01478c2312832_510",
                "http://img4.hao123.com/data/1_dd84959fa7910d741d0f4cc9dec79bdd_510",
                "http://img6.hao123.com/data/1_72b86760cbcf0a9251e4c5d28127d3f6_510",
                "http://img3.hao123.com/data/1_ae18d3a1b65a0194a8f5fa2c76f3f8a7_0",
                "http://img5.hao123.com/data/1_bc6ef28f063aa1c0d72daed48a18554a_0",
                "http://img.hao123.com/data/1_62333db73d9fa2fe2a1db6f26edab9f3_0",
                "http://img.hao123.com/data/1_0c4f1dc3daab007063fac855c9825ca5_0",
                "http://img6.hao123.com/data/1_22699180ce1bfef7db27c205a3b9cda2_0",
                "http://img4.hao123.com/data/1_758d06615bb089bcc979aa974442720a_0",
                "http://img.hao123.com/data/1_bf80a0aa0901e1a52ba2cd03c164511e_0"
        };
        return picUrls;
    }

    private void controlLoading() {
        bookLoading.start();
        bg.setVisibility(View.INVISIBLE);
        bookLoading.setVisibility(View.VISIBLE);
        myHandler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {
                                      bookLoading.stop();
                                      bookLoading.setVisibility(View.GONE);
                                      bg.setVisibility(View.VISIBLE);
                                  }
                              }
                , 5000);
    }


    public void toUpload(View view) {
        controlLoading();
    }

    public void toBack(View view) {
        finish();
    }

    private String[] names = {"系统预设", "用户自定义"};
    private int size = names.length;
    private LayoutInflater inflate;

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(names[position % names.length]);
            textView.setPadding(20, 0, 20, 0);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            GridFeatureImageFragment featureImageFragment = new GridFeatureImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(GridFeatureImageFragment.INTENT_INT_INDEX, position);
            featureImageFragment.setArguments(bundle);
            return featureImageFragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return FragmentListPageAdapter.POSITION_NONE;
        }

    }
}