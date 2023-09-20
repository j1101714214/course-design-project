package edu.whu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.model.job.pojo.XyJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyJobMapper: XyJob实体类对应的基础Mapper
 * @date 2023/9/20 20:21
 */
@Mapper
public interface XyJobMapper extends BaseMapper<XyJob> {
}
