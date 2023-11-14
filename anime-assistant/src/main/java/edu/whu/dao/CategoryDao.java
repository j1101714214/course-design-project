package edu.whu.dao;

import edu.whu.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {

}
