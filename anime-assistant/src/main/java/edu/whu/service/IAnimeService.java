package edu.whu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Anime;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */

public interface IAnimeService extends IService<Anime> {

    Anime findAnimeById(Long id);

    Page<Anime> findAnimePageByMap(Map<String, Object> params, Integer cur, Integer size);

    Long addNewAnime(Anime anime);

    boolean updateAnime(Long id, Anime anime);

    boolean delAnime(Long id);
}
