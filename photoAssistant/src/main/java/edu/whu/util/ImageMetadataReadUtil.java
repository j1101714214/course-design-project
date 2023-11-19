package edu.whu.util;

import cn.hutool.core.util.StrUtil;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.png.PngProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import edu.whu.exception.PhotoProcessException;
import edu.whu.model.pojo.PhotoMetadata;
import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;

import java.util.*;

/**
 * @author Akihabara
 * @version 1.0
 * @description ImageMetadataReadUtil: 图片元数据读取工具类
 * @date 2023/11/11 15:14
 */
public class ImageMetadataReadUtil {
    private static final String ORIGINAL_TIME = "Date/Time Original";
    private static final String GPS_LATITUDE_REF = "GPS Latitude Ref";
    private static final String GPS_LATITUDE = "GPS Latitude";
    private static final String GPS_LONGITUDE_REF = "GPS Longitude Ref";
    private static final String GPS_LONGITUDE = "GPS Longitude";
    private static final String MAKE = "Make";
    private static final String MODEL = "Model";

    private static Metadata extractMetadata(String filename) {
        File file = new File(filename);
        String suffix = getSuffix(filename);
        try {
            switch (suffix) {
                case "jpg":
                case "jpeg": {
                    return JpegMetadataReader.readMetadata(file);
                }
                case "png": {
                    return PngMetadataReader.readMetadata(file);
                }
                default:
                    return null;
            }
        } catch (JpegProcessingException | IOException | PngProcessingException e) {
            return null;
        }
    }

    private static Map<String, String> extractMetadataInMap(String filename) {
        Metadata metadata = extractMetadata(filename);
        Map<String, String> retMap = new HashMap<>();
        if(metadata == null) {
            return retMap;
        }
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                retMap.put(tag.getTagName(), tag.getDescription());
            }
        }
        return retMap;
    }

    public static String getSuffix(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1, filename.length()).toLowerCase();
    }

    public static Timestamp extractTime(Map<String, String> metadata) {
        if(metadata.containsKey(ORIGINAL_TIME)) {
            String time = metadata.get(ORIGINAL_TIME);
            time = time.replaceFirst(":", "-").replaceFirst(":", "-");
            return Timestamp.valueOf(time);
        }
        return null;
    }

    public static String extractLocation(Map<String, String> metadata) {
        if(metadata.containsKey(GPS_LATITUDE) && metadata.containsKey(GPS_LONGITUDE) ) {
            return BaiduServiceUtils.getLocationFromGPS(metadata.get(GPS_LATITUDE), metadata.get(GPS_LONGITUDE));
        }
        return null;
    }

    public static String extractDevice(Map<String, String> metadata) {
        String device = "{}-{}";
        return StrUtil.format(device, metadata.getOrDefault(MAKE, ""), metadata.getOrDefault(MODEL, ""));
    }

    public static PhotoMetadata extractPhotoMetadata(String filename) {
        Map<String, String> metadata = extractMetadataInMap(filename);
        if(metadata.size() == 0) {
            return null;
        }
        PhotoMetadata photoMetadata = new PhotoMetadata();
        photoMetadata.setPhotoName(filename);
        photoMetadata.setDevice(extractDevice(metadata));
        photoMetadata.setLocation(extractLocation(metadata));

        Timestamp timestamp = extractTime(metadata);
        if(timestamp != null) {
            photoMetadata.setOriginalTime(timestamp.toLocalDateTime().toLocalDate().toString());
        }

        return photoMetadata;
    }




//    public  static void main(String[] args) {
//        ImageMetadataReadUtil util = new ImageMetadataReadUtil();
//        System.out.println(util.extractLocation("D:\\HONOR Magic-link\\Huawei Share\\IMG_20230619_114533.jpg"));
//    }
}
