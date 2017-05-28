package com.hhxfight.recolorer.Activity.main.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;

import com.hhxfight.recolorer.config.Url;
import com.hhxfight.recolorer.util.ImageIoUtil;
import com.hhxfight.recolorer.util.ResourcesUtil;

import java.io.File;

/**
 * Created by HHX on 2017/5/10.
 */

public class PreManifoldSaveModel {
    private Context mContext;
    public PreManifoldSaveModel(Context mContext) {
        this.mContext = mContext;
    }

    public void saveManifoldIntoFile() {
        String root = Environment.getExternalStorageDirectory().toString();
        File[] files = ImageIoUtil.getImageByPaths(root + Url.APPDIR + Url.MANIFOLD +  Url.PREDEF);
        if (files != null && files.length >= 23)
            return;

        HandlerThread handlerThread = new HandlerThread("manifold");
        handlerThread.start();
        Handler subHandler = new Handler(handlerThread.getLooper());
        subHandler.post(this::saveManifoldIntoFileRunnable);
    }

    private void saveManifoldIntoFileRunnable() {
        int index = 0;
        for (Bitmap bitmap : ResourcesUtil.getMainfoldBitmaps(mContext)) {
            ImageIoUtil.saveBitmap(Url.APPDIR + Url.MANIFOLD +  Url.PREDEF, "pre" + index + ".png", bitmap);
            index++;
        }
    }


}
