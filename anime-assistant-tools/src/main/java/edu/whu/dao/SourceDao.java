package edu.whu.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.domain.Source;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
@Mapper
public interface SourceDao extends BaseMapper<Source> {

    @Select("SELECT * from api where api.source = #{id}")
    Page<Api> getApiPageBySourceId(Long id, Page<Object> objectPage);
}
