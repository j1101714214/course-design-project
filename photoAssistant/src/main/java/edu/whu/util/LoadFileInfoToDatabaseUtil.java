package edu.whu.util;

import cn.hutool.core.collection.CollUtil;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description LoadFileInfoToDatabase: 加载当前文件夹下面的图片数据到数据库
 * @date 2023/11/12 19:24
 */
public class LoadFileInfoToDatabaseUtil {
    public static void getFile(String path, List<String> uris) {
        File root = new File(path);

        if(root.isDirectory()){
            File[] files = root.listFiles();
            if(files != null && files.length > 0) {
                for(File file : files) {
                    if(file.isDirectory()) {
                        getFile(file.getAbsolutePath(), uris);
                    } else if(file.getName().endsWith("jpg") || file.getName().endsWith("png") || file.getName().endsWith("jpeg")){
                        uris.add(file.getAbsolutePath());
                    }
                }
            }
        } else{
            uris.add(path);
        }
    }

//    public static void main(String[] args) {
//        List<String> uris = new ArrayList<>();
//        getFile("D:\\xyNasToolsRoot", uris);
//        System.out.println(uris);
//    }
}
