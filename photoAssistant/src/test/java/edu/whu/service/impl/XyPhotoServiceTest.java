package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.whu.model.pojo.PhotoMetadata;
import edu.whu.service.IXyPhotoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
//@Rollback
public class XyPhotoServiceTest {
    @Autowired
    private IXyPhotoService photoService;
//
    @Test
    public void testLoadFileWithMetadata() {
        // 加载数据到数据库并提取元数据
        photoService.loadFiles("D:\\xyNasToolsRoot", 1724029438739001345L);
    }
//
//    @Test
//    public void testClassify() {
//        photoService.classify(1L);
//    }

    @Test
    public void testList() {
        List<PhotoMetadata> list = photoService.list();

        LambdaQueryWrapper<PhotoMetadata> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PhotoMetadata::getUserId, 1L);
        Map<String, List<PhotoMetadata>> map = photoService.listByCriteria("设备名", 1724029438739001345L);
        Assertions.assertEquals(2, map.size());     // 一共两类
    }
}
