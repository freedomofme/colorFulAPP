#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
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
        JNIEnv *env,
        jclass jobject ) {
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

/*
 * Class:     com_hhxfight_recolorer_util_NativeCode
 * Method:    ReverseImage
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_hhxfight_recolorer_util_NativeCode_ReverseImage
        (JNIEnv *env, jclass obj, jlong srcImage, jlong resultImage) {
    Mat target_IMG = *(Mat *) srcImage;
    Mat compare_IMG = *(Mat *) resultImage;

    for (int i = 0; i < target_IMG.rows; i++) {
        for (int j = 0; j < target_IMG.cols; j++) {
            compare_IMG.at<uchar>(i, j) = 255 - target_IMG.at<uchar>(i, j);
        }
    }
}
