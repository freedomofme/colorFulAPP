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

#define  LOG_TAG    "Tag"

#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

/*+--------+----+----+----+----+------+------+------+------+
|        | C1 | C2 | C3 | C4 | C(5) | C(6) | C(7) | C(8) |
+--------+----+----+----+----+------+------+------+------+
| CV_8U  |  0 |  8 | 16 | 24 |   32 |   40 |   48 |   56 |
| CV_8S  |  1 |  9 | 17 | 25 |   33 |   41 |   49 |   57 |
| CV_16U |  2 | 10 | 18 | 26 |   34 |   42 |   50 |   58 |
| CV_16S |  3 | 11 | 19 | 27 |   35 |   43 |   51 |   59 |
| CV_32S |  4 | 12 | 20 | 28 |   36 |   44 |   52 |   60 |
| CV_32F |  5 | 13 | 21 | 29 |   37 |   45 |   53 |   61 |
| CV_64F |  6 | 14 | 22 | 30 |   38 |   46 |   54 |   62 |
+--------+----+----+----+----+------+------+------+------+*/

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
        (JNIEnv *env, jobject obj, jlong srcImage, jlong resultImage) {
    Mat src = *(Mat *) srcImage;
    Mat result = *(Mat *) resultImage;

    for (int i = 0; i < src.rows; i++) {
        // RGB, not Alpha, so just use Vec3b, not Vec4b
        for (int j = 0; j < src.cols; j++) {
            result.at<cv::Vec4b>(i, j)[0] = 255 - src.at<cv::Vec4b>(i, j)[0];
            result.at<cv::Vec4b>(i, j)[1] = 255 - src.at<cv::Vec4b>(i, j)[1];
            result.at<cv::Vec4b>(i, j)[2] = 255 - src.at<cv::Vec4b>(i, j)[2];
            result.at<cv::Vec4b>(i, j)[3] = src.at<cv::Vec4b>(i, j)[3];

        }
    }
    LOGD( "This is a number from JNI: %d", src.at<uchar>(0, 0) );
    LOGD( "This is a number from JNI: %d", result.at<uchar>(0, 0) );
    std::string hello = "reverse finished！！";

    return env->NewStringUTF(hello.c_str());
}

