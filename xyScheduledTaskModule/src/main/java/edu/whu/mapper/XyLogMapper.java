package edu.whu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.model.log.pojo.XyLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyLogMapper: XyLog实体类对应的基础Mapper
 * @date 2023/10/6 20:06
 */
@Mapper
public interface XyLogMapper extends BaseMapper<XyLog> {
}
