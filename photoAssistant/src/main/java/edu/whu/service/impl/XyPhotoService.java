package edu.whu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drew.metadata.Face;
import edu.whu.exception.PhotoProcessException;
import edu.whu.mapper.PhotoMapper;
import edu.whu.model.pojo.PhotoMetadata;
import edu.whu.service.IXyPhotoService;
import edu.whu.util.FaceDetectionUtils;
import edu.whu.util.ImageMetadataReadUtil;
import edu.whu.util.LoadFileInfoToDatabaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyPhotoService: 图片管理服务实现类
 * @date 2023/11/12 19:39
 */
@Service
@Transactional
public class XyPhotoService extends ServiceImpl<PhotoMapper, PhotoMetadata> implements IXyPhotoService {
    @Autowired
    private PhotoMapper photoMapper;
    @Value("${photo-plugin.root}")
    private String root;

    @Override
    public void loadFiles(String root, Long userId) {
        if(!deleteFileByUserId(userId)) {
            throw new PhotoProcessException("初始化用户图片元数据数据库失败");
        }
        List<String> uris = new ArrayList<>();
        LoadFileInfoToDatabaseUtil.getFile(root, uris);

        List<PhotoMetadata> photoMetadataList = new ArrayList<>();
        if(uris.size() > 0) {
            for (String uri : uris) {
                PhotoMetadata photoMetadata = ImageMetadataReadUtil.extractPhotoMetadata(uri);
                if(photoMetadata != null) {
                    photoMetadata.setUserId(userId);
//                    System.out.println(photoMetadata.getPhotoName());
//                    System.out.println(root);
                    photoMetadata.setPhotoName(photoMetadata.getPhotoName().replace(root, ""));
                    photoMetadataList.add(photoMetadata);
                }
            }
        }

        saveBatch(photoMetadataList);

    }

    @Override
    public void classify(Long userId) {
        List<PhotoMetadata> metadata = getPhotoMetadataByUserId(userId);
        for (PhotoMetadata photoMetadata : metadata) {
            photoMetadata.setCategory(photoMetadata.getId());
        }

        Map<Long, Long> map = metadata.stream().collect(Collectors.toMap(PhotoMetadata::getId, PhotoMetadata::getCategory));
        FaceDetectionUtils.classify(metadata ,map);

        // 更新类别数据
        updateBatchById(metadata);
    }

    @Override
    public Map<String, List<PhotoMetadata>> listByCriteria(String criteria, Long userId) {
        LambdaQueryWrapper<PhotoMetadata> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PhotoMetadata::getUserId, userId);
        List<PhotoMetadata> photoMetadata = photoMapper.selectList(lqw);

        Map<String, List<PhotoMetadata>> ret = new HashMap<>();

        switch (criteria) {
            case "设备名": {
                // 按设备名分类
                ret = photoMetadata.stream().collect(Collectors
                        .groupingBy(item -> Optional.ofNullable(item.getDevice()).orElse("其他")));
                break;
            }
            case "时间": {
                // 按时间分类
                ret = photoMetadata.stream().collect(Collectors
                        .groupingBy(photoMetadata1 -> {
                            if(StrUtil.isNullOrUndefined(photoMetadata1.getOriginalTime())) {
                                return "其他";
                            } else {
                                return photoMetadata1.getOriginalTime();
                            }

                        }));
                break;
            }
            case "地点": {
                // 按地点分类
                ret = photoMetadata.stream().collect(Collectors
                        .groupingBy(item -> Optional.ofNullable(item.getLocation()).orElse("其他")));
                break;
            }
            default: {
                ret.put("所有图片", photoMetadata);
                break;
            }
        }

        return ret;
    }

    /**
     * 删除当前用户的所有数据
     * @param userId    用户id
     * @return          操作结果
     */
    private boolean deleteFileByUserId(Long userId) {
        LambdaQueryWrapper<PhotoMetadata> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PhotoMetadata::getUserId, userId);

        return photoMapper.delete(lqw) >= 0;
    }

    /**
     * 根据用户id查询器图片数据
     * @param userId    用户id
     * @return          用户存储图片数据
     */
    private List<PhotoMetadata> getPhotoMetadataByUserId(Long userId) {
        LambdaQueryWrapper<PhotoMetadata> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PhotoMetadata::getUserId, userId);
        return photoMapper.selectList(lqw);
    }

}
