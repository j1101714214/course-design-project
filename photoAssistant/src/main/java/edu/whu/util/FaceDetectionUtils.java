package edu.whu.util;

import edu.whu.model.pojo.PhotoMetadata;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Akihabara
 * @version 1.0
 * @description FaceDetectionUtils: 人脸识别工具类
 * @date 2023/11/11 15:09
 */
public class FaceDetectionUtils {
    // 初始化人脸探测器
    static CascadeClassifier faceDetector;

    public static void classify(List<PhotoMetadata> metadata, Map<Long, Long> map) {
        for(int i = 0; i < metadata.size(); ++i) {
            for(int j = i + 1; j < metadata.size(); ++j) {
                try {
                    if (compare_image(metadata.get(i).getPhotoName(), metadata.get(j).getPhotoName()) >= 0.8) {
                        Long iid = metadata.get(i).getId();
                        Long jid = metadata.get(j).getId();
                        while(!iid.equals(map.get(iid))) {
                            iid = map.get(iid);
                        }

                        while(!jid.equals(map.get(jid))) {
                            jid = map.get(jid);
                        }

                        map.replace(Math.max(iid, jid), Math.min(iid, jid));
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }

        // 统一
        for (PhotoMetadata photoMetadata : metadata) {
            Long iid = map.get(photoMetadata.getId());
            while (!iid.equals(map.get(iid))) {
                iid = map.get(iid);
            }
            photoMetadata.setCategory(iid);
        }
    }

    static  {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 引入 特征分类器配置 文件：haarcascade_frontalface_alt.xml 文件路径
        // 此文件在opencv的安装目录build\etc\haarcascades下可以找到
        String property = "D:\\haarcascade_frontalface_default.xml";
        faceDetector = new CascadeClassifier(property);
    }

    // 灰度化人脸
    public static Mat conv_Mat(String img) {
        Mat image0 = Imgcodecs.imread(img);
        Mat image1 = new Mat();
        // 灰度化
        Imgproc.cvtColor(image0, image1, Imgproc.COLOR_BGR2GRAY);
        // 探测人脸
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image1, faceDetections);
        // rect中人脸图片的范围
        for (Rect rect : faceDetections.toArray()) {
            return new Mat(image1, rect);
        }
        return null;
    }

    // 比较图片
    public static double compare_image(String img_1, String img_2) {
        Mat mat_1 = conv_Mat(img_1);
        Mat mat_2 = conv_Mat(img_2);
        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();
        //颜色范围
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        //直方图大小， 越大匹配越精确 (越慢)
        MatOfInt histSize = new MatOfInt(10000000);
        Imgproc.calcHist(Collections.singletonList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Collections.singletonList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);
        // CORREL 相关系数
        return Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
    }

    public static void main(String[] args) {
        Mat imread = Imgcodecs.imread("D:\\lena.png");
        System.out.println(imread);
    }
}
