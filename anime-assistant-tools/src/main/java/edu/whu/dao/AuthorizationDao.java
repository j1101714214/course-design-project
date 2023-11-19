package edu.whu.dao;

import edu.whu.domain.Authorization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
@Mapper
public interface AuthorizationDao extends BaseMapper<Authorization> {

}
