package com.hhxfight.recolorer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HHX on 2017/5/10.
 */

public class ResourcesUtil {
    public static Bitmap getBitmapFromRes(Context context, int rid) {
        Drawable d = context.getResources().getDrawableForDensity(rid, 1, null);
        Drawable currentState = d.getCurrent();
        if(currentState instanceof BitmapDrawable) {
            return ((BitmapDrawable) currentState).getBitmap();
        } else
            return null;
    }

    public static List<Integer> getMainfoldDrawableId(Context context) {
        ArrayList<Integer> manifoldList = new ArrayList<>();
        for (int i = 0; i <= 25; i++) {
            int drawable = context.getResources().getIdentifier("sys" + i + "",
                    "drawable", context.getPackageName());
            manifoldList.add(drawable);
        }

        return manifoldList;
    }

    public static List<Bitmap> getMainfoldBitmaps(Context context) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (Integer id : getMainfoldDrawableId(context)) {
//            Bitmap temp = getBitmapFromRes(context, id);
            Bitmap temp = ImageIoUtil.decodeSampledRes(context.getResources(), id, 100, 100);
            if ( temp != null) {
                bitmaps.add(temp);
            }
        }
        return bitmaps;
    }


}
