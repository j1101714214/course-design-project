package edu.whu.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Anime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
@Mapper
public interface AnimeDao extends BaseMapper<Anime> {

    @Select("SELECT anime FROM map, anime WHERE map.cate_id = #{cateId} " +
            "and map.anime_id = anime.id and anime.ani_initial_date = CONCAT ('%', #{date},'%') " +
            "and anime.name LIKE CONCAT ('%', #{name},'%')")
    Page<Anime> findAnimesByCate(Long cateId, String date, String name, Integer cur, Integer size);
}
