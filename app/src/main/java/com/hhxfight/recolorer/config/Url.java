package com.hhxfight.recolorer.config;

/**
 * Created by HHX on 2017/3/23.
 */

public class Url {
    private static final String pre = "http://192.168.253.5";
    private static final String port = ":5000";

//    private static final String pre = "http://101.200.58.119";
//    private static final String port = ":80";
    public static final String test = pre + port + "/uploadFile";
    public static final String uploadImage = pre + port + "/uploadFile";
    public static final String uploadImageNotCreateM = pre + port + "/uploadFileNotCreateM";
    public static final String gray = pre + port + "/gray";
    public static final String recolor = pre + port + "/recolor";

    public static final String colorImage = pre + port + "/r";
    public static final String grayImage = pre + port + "/g";
    public static final String m1Image = pre + port + "/m1";
    public static final String m2Image = pre + port + "/m2";


    public static final String APPDIR = "/recolor";
    public static final String MANIFOLD = "/manifold";
    public static final String GRAY = "/gray";
    public static final String COLOR = "/color";
    public static final String PREDEF = "/pre";
    public static final String USERDEF = "/user";
}
