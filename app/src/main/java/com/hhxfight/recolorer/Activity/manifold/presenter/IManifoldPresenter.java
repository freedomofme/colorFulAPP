package com.hhxfight.recolorer.Activity.manifold.presenter;

import android.graphics.Bitmap;

/**
 * Created by HHX on 2017/5/5.
 */

public interface IManifoldPresenter {
    void postImage(String path);
    void getManifold(int choice);
    void saveManifold(Bitmap bitmap);
}
