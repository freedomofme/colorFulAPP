package com.hhxfight.recolorer.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HHX on 2017/5/9.
 */

public class ImageIoUtil {
    public static String saveBitmap(String dir, String filename, Bitmap bitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        FileOutputStream out = null;
        File file = null;
        try {
            file = new File(root, dir + "/" + filename);
            File dirFile = new File(root, dir);
            if (!dirFile.exists()) {
                final boolean mkdir = dirFile.mkdirs();
            }
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file != null ? file.getAbsolutePath() : null;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    || (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmap(String strPath, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(strPath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,
                reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(strPath, options);
    }

    public static Bitmap decodeSampledRes(Resources res, int id, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inScaled = false;
        BitmapFactory.decodeResource(res, id, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,
                reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }

    public static Bitmap decodeSampledFile(String path, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,
                reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static File[] getImageByPaths(String path) {
        List<String> paths = new ArrayList<>();
        File f = new File(path);
        if (!f.exists())
            f.mkdir();
        if (f.isDirectory()) {
            return f.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
        }
        return null;
    }
}
