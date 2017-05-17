package com.hhxfight.recolorer.Activity.color.view;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by HHX on 2017/5/17.
 */

public interface IColorTransformView {
    void onImagePosted(String path);
    void onTransformedImageGet(ImageLoader.ImageContainer imageContainer);
}
