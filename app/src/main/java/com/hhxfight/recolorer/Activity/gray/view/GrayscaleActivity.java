
package com.hhxfight.recolorer.Activity.gray.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.boycy815.pinchimageview.PinchImageView;
import com.hhxfight.recolorer.Activity.gray.presenter.GrayscalePresenter;
import com.hhxfight.recolorer.Activity.gray.presenter.IGrayPresenter;
import com.hhxfight.recolorer.Activity.mywork.MyWorkActivity;
import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.base.BaseActivity;
import com.hhxfight.recolorer.widget.GroupButtonView;
import com.victor.loading.book.BookLoading;
import com.yanzhenjie.album.Album;

import java.util.List;


public class GrayscaleActivity extends BaseActivity implements IGaryView{

    PinchImageView bg;
    BookLoading bookLoading;
    GroupButtonView gbv_quality;
    //	RecyclerView recyclerView;
    final Handler myHandler = new Handler();
    IGrayPresenter grayscalePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gray);
        grayscalePresenter = new GrayscalePresenter(this, this);

        bg = (PinchImageView) findViewById(R.id.piv_bg);
        bookLoading = (BookLoading) findViewById(R.id.bl_bookloading);
        bookLoading.setVisibility(View.INVISIBLE);
        gbv_quality = (GroupButtonView) findViewById(R.id.gbv_quality);
        gbv_quality.setCheckedByIndex(2);

//        Bitmap bitmap = AssetsUtil.getBitmap(this, "highGray.png");
//        bg.setImageBitmap(bitmap);

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Album.album(GrayscaleActivity.this)
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

        gbv_quality.setOnGroupBtnClickListener(new GroupButtonView.OnGroupBtnClickListener() {
            @Override
            public void groupBtnClick(String code) {
                startLoading();

//                myHandler.postDelayed(() -> stopLoading()
//                        , 5000);
                if (code.equals("high")) {
                    grayscalePresenter.postImage(null, 3);
                } else if (code.equals("mid")) {
                    grayscalePresenter.postImage(null, 2);
                } else {
                    grayscalePresenter.postImage(null, 1);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 999) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                Log.d("TAG", pathList.toString());
                startLoading();
                grayscalePresenter.postImage(pathList.get(0), 3);
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
    }


    @Override
    public void onImgaePosted() {
        Toast.makeText(this, "图片上传完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageGrayed(String gid) {
//        Toast.makeText(this, "图片灰度化完成", Toast.LENGTH_SHORT).show();
//        MySingleton.getInstance(this.getApplicationContext()).getImageLoader().get(Url.grayImage + "/" + gid, ImageLoader.getImageListener(bg, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
    }

    @Override
    public void onGrayedImageGet(ImageLoader.ImageContainer imageContainer) {
        Toast.makeText(this, "图片灰度化完成", Toast.LENGTH_SHORT).show();
        bg.setImageBitmap(imageContainer.getBitmap());
        stopLoading();
    }

    public void toSave(View v) {
        grayscalePresenter.saveGray(bg);
        Toast.makeText(this, "灰度图像已保存", Toast.LENGTH_SHORT).show();
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
}