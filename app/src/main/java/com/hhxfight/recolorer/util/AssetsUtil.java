package com.hhxfight.recolorer.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import java.io.IOException;
import java.io.InputStream;

import static android.graphics.BitmapFactory.decodeStream;

/**
 * Created by HHX on 2017/4/6.
 */

public class AssetsUtil {
    public static Bitmap getBitmap(Context context, String path) {
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(path);
            return decodeStream(is);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
