package edu.whu.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
public interface ApiDao extends BaseMapper<Api> {

    @Select("SELECT api.* from api, source where api.source = source.id and source.name = #{name} ")
    Page<Api> getPageBySource(String name, Page<Object> objectPage);

    @Select("SELECT api.* from api, source where api.source = source.id and source.name = #{name} ")
    List<Api> getListBySource(String name);
}
