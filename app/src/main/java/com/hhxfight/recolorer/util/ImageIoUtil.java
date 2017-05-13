package com.hhxfight.recolorer.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
}
