package com.hhxfight.recolorer.Activity.manifold.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.boycy815.pinchimageview.PinchImageView;
import com.hhxfight.recolorer.Activity.manifold.presenter.IManifoldPresenter;
import com.hhxfight.recolorer.Activity.manifold.presenter.ManifoldPresenter;
import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.base.BaseActivity;
import com.hhxfight.recolorer.widget.GroupButtonView;
import com.victor.loading.book.BookLoading;
import com.yanzhenjie.album.Album;

import java.util.List;


public class ManifoldActivity extends BaseActivity implements IManifoldView{
    PinchImageView bg;
    BookLoading bookLoading;
    GroupButtonView gbv_quality;
    //	RecyclerView recyclerView;
    final Handler myHandler = new Handler();
    IManifoldPresenter manifoldPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifold);

        manifoldPresenter = new ManifoldPresenter(this, this);

        bg = (PinchImageView) findViewById(R.id.piv_bg);
        bookLoading = (BookLoading) findViewById(R.id.bl_bookloading);
        bookLoading.setVisibility(View.INVISIBLE);
        gbv_quality = (GroupButtonView) findViewById(R.id.gbv_quality);


//        recyclerView = (RecyclerView) findViewById(R.id.rl_login);
//        Bitmap bitmap = AssetsUtil.getBitmap(this, "oneDim.png");
//        bg.setImageBitmap(bitmap);

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Album.album(ManifoldActivity.this)
                        .requestCode(999) // 请求码，返回时onActivityResult()的第一个参数。
//						.toolBarColor(toolbarColor) // Toolbar 颜色，默认蓝色。
//						.statusBarColor(statusBarColor) // StatusBar 颜色，默认蓝色。
//						.navigationBarColor(navigationBarColor) // NavigationBar 颜色，默认黑色，建议使用默认。
                        .title("图库") // 配置title。
                        .selectCount(9) // 最多选择几张图片。
                        .columnCount(2) // 相册展示列数，默认是2列。
                        .camera(true) // 是否有拍照功能。
//						.checkedList() // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                        .start();
            }
        }, 400);

        gbv_quality.setOnGroupBtnClickListener(new GroupButtonView.OnGroupBtnClickListener() {
            @Override
            public void groupBtnClick(String code) {
//				Toast.makeText(GrayscaleActivity.this, code, Toast.LENGTH_SHORT).show();
                if (code.equals("one")) {
                    startLoading();
                    manifoldPresenter.getManifold(1);
//                    Bitmap bitmap = AssetsUtil.getBitmap(ManifoldActivity.this, "oneDim.png");
//                    bg.setImageBitmap(bitmap);
                } else {
                    startLoading();
                    manifoldPresenter.getManifold(2);
                }
        }});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                Log.d("TAG", pathList.toString());
                startLoading();
                manifoldPresenter.postImage(pathList.get(0));
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
    }

    @Override
    public void onImagePosted(String sid) {
        Toast.makeText(this, "图片上传完成", Toast.LENGTH_SHORT).show();
        manifoldPresenter.getManifold(1);
    }

    @Override
    public void onManifoldGet(ImageLoader.ImageContainer imageContainer, int choice) {
        if (choice == 1)
            Toast.makeText(this, "一维流形已获取", Toast.LENGTH_SHORT).show();
        else if (choice == 2)
            Toast.makeText(this, "二维流形已获取", Toast.LENGTH_SHORT).show();
        stopLoading();
        bg.setImageBitmap(imageContainer.getBitmap());
    }

    private void startLoading() {
        bookLoading.start();
        bg.setVisibility(View.INVISIBLE);
        bookLoading.setVisibility(View.VISIBLE);
    }
    private void stopLoading() {
        bookLoading.stop();
        bg.setVisibility(View.VISIBLE);
        bookLoading.setVisibility(View.INVISIBLE);
    }

}