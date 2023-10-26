package edu.whu.config;


import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author Akihabara
 * @version 1.0
 * @description LoadOpenCVTest: 加载OpenCV库测试
 * @date 2023/10/26 19:19
 */
@SpringBootTest
public class LoadOpenCVTest {
    @Test
    public void testLoadOpenCV() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imread = Imgcodecs.imread("D:/lena.png");
        System.out.println("mat = " + imread.size());
    }
}
