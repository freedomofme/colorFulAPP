package com.hhxfight.recolorer.Activity.gray.view;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by HHX on 2017/5/3.
 */

public interface IGaryView{
    void onImgaePosted();
    void onImageGrayed(String gid);
    void onGrayedImageGet(ImageLoader.ImageContainer imageContainer);
}
