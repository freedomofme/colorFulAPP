package com.hhxfight.recolorer.Activity.color;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.boycy815.pinchimageview.PinchImageView;
import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.base.BaseActivity;
import com.hhxfight.recolorer.util.AssetsUtil;
import com.hhxfight.recolorer.widget.SimpleDividerItemDecoration;
import com.victor.loading.book.BookLoading;
import com.yanzhenjie.album.Album;

/**
 * Created by HHX on 2017/4/6.
 */


public class ColorTransform extends BaseActivity {

    PinchImageView bg;
    RecyclerView recyclerView;
    BookLoading bookLoading;
    final Handler myHandler = new Handler();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        bg = (PinchImageView) findViewById(R.id.piv_bg);
        recyclerView = (RecyclerView) findViewById(R.id.rv_color);
        Bitmap bitmap = AssetsUtil.getBitmap(this, "apple_0.png");
        bg.setImageBitmap(bitmap);
        bookLoading = (BookLoading) findViewById(R.id.bl_bookloading);
        bookLoading.setVisibility(View.GONE);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Album.album(ColorTransform.this)
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


        adapter = new MyAdapter(this, getPicUrls());

//        useLinearLayoutManager();
        useGridLayoutManager();
//      useStaggeredGridLayoutManager();

        recyclerView.setAdapter(adapter);

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

    public void toBack(View view){
        finish();
    }
}