
package com.hhxfight.recolorer.Activity.mywork;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import com.boycy815.pinchimageview.PinchImageView;
import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.base.BaseActivity;
import com.hhxfight.recolorer.util.AssetsUtil;
import com.hhxfight.recolorer.widget.GroupButtonView;
import com.victor.loading.book.BookLoading;
import com.yanzhenjie.album.Album;


public class MyWorkActivity extends BaseActivity {
    PinchImageView bg;
    BookLoading bookLoading;
    GroupButtonView gbv_quality;
    //	RecyclerView recyclerView;
    final Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gray);

        bg = (PinchImageView) findViewById(R.id.piv_bg);
        bookLoading = (BookLoading) findViewById(R.id.bl_bookloading);
        gbv_quality = (GroupButtonView) findViewById(R.id.gbv_quality);


//        recyclerView = (RecyclerView) findViewById(R.id.rl_login);
        Bitmap bitmap = AssetsUtil.getBitmap(this, "apple_0.png");
        bg.setImageBitmap(bitmap);

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Album.album(MyWorkActivity.this)
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
        }, 2000);

        gbv_quality.setOnGroupBtnClickListener(new GroupButtonView.OnGroupBtnClickListener() {
            @Override
            public void groupBtnClick(String code) {
//				Toast.makeText(GrayscaleActivity.this, code, Toast.LENGTH_SHORT).show();
                bookLoading.start();
                myHandler.postDelayed(new Runnable() {
                                          @Override
                                          public void run() {
                                              bookLoading.stop();
                                          }
                                      }
                        , 1000);
            }
        });


//	private void useLinearLayoutManager() {
//		// 创建线性布局管理器
//		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//		// 设置显示布局的方向，默认方向是垂直
////        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//		// 设置布局管理器
//		recyclerView.setLayoutManager(linearLayoutManager);
//	}


    }
}