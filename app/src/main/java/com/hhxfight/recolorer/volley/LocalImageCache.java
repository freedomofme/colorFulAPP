package com.hhxfight.recolorer.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by HHX on 2017/3/24.
 */

public class LocalImageCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public LocalImageCache(int maxSize) {
        super(maxSize);
    }

    public LocalImageCache(Context ctx) {
        this(getCacheSize(ctx));
    }

    @Override
    public Bitmap getBitmap(String key) {
        key = key.substring(key.indexOf("/"));
        Bitmap result = get(key);
        Log.d("TAG", key);
        if (result == null) {
            Bitmap temp =  BitmapFactory.decodeFile(key);
            put(key, temp);
            return temp;
        } else {
            return result;
        }
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        // Here you can add an actual cache
    }

    // 默认屏幕5倍的图片缓存
    // Returns a cache size equal to approximately three screens worth of images.
    public static int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 5;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
}