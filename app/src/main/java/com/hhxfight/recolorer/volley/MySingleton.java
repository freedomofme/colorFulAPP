package com.hhxfight.recolorer.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private ImageLoader mLocalImageLoader;
    private static Context mCtx;

    private MySingleton(Context context) {
        mCtx = context;

        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new NetworkImageLruCache(mCtx), 1 * 3600 * 1000, 15 * 24 * 3600 * 1000, true);
        mLocalImageLoader = new ImageLoader(mRequestQueue,
                new LocalImageCache(mCtx));
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    //30M的硬盘缓存
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), null, 30 * 1024 * 1024);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public ImageLoader getLocalImageLoader() {
        return mLocalImageLoader;
    }

    public void cancelAllPendingRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestFilter(){
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null && tag != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}