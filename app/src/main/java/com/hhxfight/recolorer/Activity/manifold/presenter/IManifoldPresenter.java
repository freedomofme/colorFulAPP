package com.hhxfight.recolorer.Activity.manifold.presenter;

import android.view.View;

/**
 * Created by HHX on 2017/5/5.
 */

public interface IManifoldPresenter {
    void postImage(String path);
    void getManifold(int choice);
    void saveManifold(View v);
}
