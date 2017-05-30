#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <string>
#include <android/log.h>
#include <opencv/cv.h>
#include <opencv/cxcore.h>
#include <opencv/highgui.h>
#include <opencv2/opencv.hpp>

using namespace std;
using namespace cv;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_hhxfight_recolorer_Activity_main_view_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_hhxfight_recolorer_Activity_main_view_MainActivity_reverse(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++!!!";

    return env->NewStringUTF(hello.c_str());
}

/*
 * Class:     com_hhxfight_recolorer_util_NativeCode
 * Method:    ReverseImage
 * Signature: (JJ)V
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_com_hhxfight_recolorer_Activity_gray_presenter_GrayscalePresenter_reverseImage
        (JNIEnv *env, jobject obj) {
    /*Mat target_IMG = *(Mat *) srcImage;
    Mat compare_IMG = *(Mat *) resultImage;

    for (int i = 0; i < target_IMG.rows; i++) {
        for (int j = 0; j < target_IMG.cols; j++) {
            compare_IMG.at<uchar>(i, j) = 255 - target_IMG.at<uchar>(i, j);
        }
    }*/
    std::string hello = "Hello from C++!!!";

    return env->NewStringUTF(hello.c_str());
}

