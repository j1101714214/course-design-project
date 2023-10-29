package edu.whu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.model.user.pojo.XyUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyUserMapper: XyUser实体类对应的基础Mapper
 * @date 2023/9/16 15:49
 */
@Mapper
public interface XyUserMapper extends BaseMapper<XyUser> {
}
