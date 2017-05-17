package com.hhxfight.recolorer.bean;

import android.graphics.Bitmap;

/**
 * Created by HHX on 2017/5/17.
 */

public class LocalImageBean {
    public LocalImageBean() {

    }
    public LocalImageBean(Bitmap bitmap, String absPath) {
        this.bitmap = bitmap;
        this.absPath = absPath;
    }
    public Bitmap bitmap;
    public String absPath;
}
