package com.hhxfight.recolorer.Activity.color.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.boycy815.pinchimageview.PinchImageView;
import com.hhxfight.recolorer.Activity.color.presenter.ColorTransformPresenter;
import com.hhxfight.recolorer.Activity.color.presenter.IColorPresenter;
import com.hhxfight.recolorer.Activity.mywork.MyWorkActivity;
import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.util.ImageIoUtil;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.victor.loading.book.BookLoading;
import com.yanzhenjie.album.Album;

import java.util.List;

/**
 * Created by HHX on 2017/4/6.
 */


public class ColorTransformActivity extends FragmentActivity implements IColorTransformView{

    PinchImageView bg;
    RecyclerView recyclerView;
    BookLoading bookLoading;
    IColorPresenter iColorPresenter;
    Bitmap colorBitmapTemp;
    final Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        inflate = LayoutInflater.from(getBaseContext());

        iColorPresenter = new ColorTransformPresenter(this, this);

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

//        Bitmap bitmap = AssetsUtil.getBitmap(this, "greenAni.png");
//        bg.setImageBitmap(bitmap);
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
        }, 300);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                Log.d("TAG", pathList.toString());
                startLoading();
                iColorPresenter.postImage(pathList.get(0));
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    public void toSave(View v) {
        iColorPresenter.saveColor(colorBitmapTemp);
        Toast.makeText(this, "换色图像已保存", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MyWorkActivity.class));
        finish();
    }


    private void stopLoading() {
        bookLoading.stop();
        bookLoading.setVisibility(View.INVISIBLE);
        bg.setVisibility(View.VISIBLE);
    }

    private void startLoading() {
        bookLoading.start();
        bg.setVisibility(View.INVISIBLE);
        bookLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onImagePosted(String path) {
        stopLoading();
        Bitmap bitmap = ImageIoUtil.decodeSampledBitmap(path, 600, 600);
        bg.setImageBitmap(bitmap);

        Toast.makeText(this, "图片上传完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransformedImageGet(ImageLoader.ImageContainer imageContainer) {
        Toast.makeText(this, "图片色彩转移完成", Toast.LENGTH_SHORT).show();
        colorBitmapTemp = imageContainer.getBitmap();
        bg.setImageBitmap(colorBitmapTemp);
        stopLoading();
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