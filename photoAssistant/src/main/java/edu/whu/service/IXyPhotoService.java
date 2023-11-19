package edu.whu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.mapper.PhotoMapper;
import edu.whu.model.pojo.PhotoMetadata;

import java.util.List;
import java.util.Map;

/**
 * @author Akihabara
 * @version 1.0
 * @description IXyPhotoService: PhotoMetadata对应的服务层接口
 * @date 2023/11/12 19:37
 */
public interface IXyPhotoService extends IService<PhotoMetadata> {
    /**
     * 为用户加载所有的图片数据, 并解析解析元数据
     * @param root      根路径
     * @param userId    用户id
     */
    void loadFiles(String root, Long userId);

    /**
     * 识别图片相似度
     * @param userId    用户id
     */
    void classify(Long userId);

    /**
     * 根据分类标准和用户获取图片
     * @param criteria  标准
     * @param userId    用户id
     * @return          查询结果
     */
    Map<String, List<PhotoMetadata>> listByCriteria(String criteria, Long userId);
}
