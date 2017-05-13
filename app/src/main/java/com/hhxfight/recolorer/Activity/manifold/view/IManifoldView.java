package com.hhxfight.recolorer.Activity.manifold.view;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by HHX on 2017/5/5.
 */

public interface IManifoldView {
    void onImagePosted(String sid);
    void onManifoldGet(ImageLoader.ImageContainer imageContainer, int choice);
}
