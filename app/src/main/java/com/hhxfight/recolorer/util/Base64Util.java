package com.hhxfight.recolorer.util;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HHX on 2017/5/3.
 */

public class Base64Util {
    public static String getImageStr(String filePath) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        //返回Base64编码过的字节数组字符串
        return Base64.encodeToString(data,Base64.DEFAULT);
    }
}
