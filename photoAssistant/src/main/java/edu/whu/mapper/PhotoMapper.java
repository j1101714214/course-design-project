package edu.whu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.model.pojo.PhotoMetadata;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akihabara
 * @version 1.0
 * @description PhotoMapper: PhotoMetadata实体类对应的基础Mapper
 * @date 2023/11/12 19:37
 */
@Mapper
public interface PhotoMapper extends BaseMapper<PhotoMetadata> {
}
