package edu.whu.config;


import org.junit.jupiter.api.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;


/**
 * @author Akihabara
 * @version 1.0
 * @description LoadOpenCVTest: 加载OpenCV库测试
 * @date 2023/10/26 19:19
 */
@SpringBootTest
public class LoadOpenCVTest {
//    @Test
//    public void testLoadOpenCV() {
//
//    }
//
//    // 初始化人脸探测器
//    static CascadeClassifier faceDetector;
//
//    static {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        // 引入 特征分类器配置 文件：haarcascade_frontalface_alt.xml 文件路径
//        // 此文件在opencv的安装目录build\etc\haarcascades下可以找到
//        String property = "D:\\haarcascade_frontalface_default.xml";
//        faceDetector = new CascadeClassifier(property);
//    }
//
//    public static void main(String[] args) {
//        // 图片路径不能包含中文
//        String str1 = "D:\\lena.png";
//        String str2 = "D:\\lena.png";
//        long start = System.currentTimeMillis();
//        double compareHist = compare_image(str1, str2);
//        System.out.println("time:" + (System.currentTimeMillis() - start));
//        System.out.println(compareHist);
//        if (compareHist > 0.6) {
//            System.out.println("人脸匹配");
//        } else {
//            System.out.println("人脸不匹配");
//        }
//    }
//
//    // 灰度化人脸
//    public static Mat conv_Mat(String img) {
//        Mat image0 = Imgcodecs.imread(img);
//        Mat image1 = new Mat();
//        // 灰度化
//        Imgproc.cvtColor(image0, image1, Imgproc.COLOR_BGR2GRAY);
//        // 探测人脸
//        MatOfRect faceDetections = new MatOfRect();
//        faceDetector.detectMultiScale(image1, faceDetections);
//        // rect中人脸图片的范围
//        for (Rect rect : faceDetections.toArray()) {
//            return new Mat(image1, rect);
//        }
//        return null;
//    }
//
//    // 比较图片
//    public static double compare_image(String img_1, String img_2) {
//        Mat mat_1 = conv_Mat(img_1);
//        Mat mat_2 = conv_Mat(img_2);
//        Mat hist_1 = new Mat();
//        Mat hist_2 = new Mat();
//        //颜色范围
//        MatOfFloat ranges = new MatOfFloat(0f, 256f);
//        //直方图大小， 越大匹配越精确 (越慢)
//        MatOfInt histSize = new MatOfInt(10000000);
//        Imgproc.calcHist(Collections.singletonList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
//        Imgproc.calcHist(Collections.singletonList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);
//        // CORREL 相关系数
//        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
//        return res;
//    }

}
