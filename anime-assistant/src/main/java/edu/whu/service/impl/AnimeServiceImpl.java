package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Anime;
import edu.whu.dao.AnimeDao;
import edu.whu.service.IAnimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
@Service
public class AnimeServiceImpl extends ServiceImpl<AnimeDao, Anime> implements IAnimeService {

    @Autowired
    private BaseMapper<Anime> animeBaseMapper;
    @Override
    public Anime findAnimeById(Long id) {
        if(id == null) return null;
        Anime res = getBaseMapper().selectById(id);
        return res;
    }

    @Override
    public Page<Anime> findAnimePageByMap(Map<String, Object> params, Integer cur, Integer size) {
        if (params.containsKey("cate")) {
            Long cateId = (Long) params.get("cate");
            String date = "", name = "";
            boolean flag1 = false, flag2 = false;
            if(params.containsKey("name")) {
                name = (String) params.get("name");
                flag1  = true;
            }

            if(params.containsKey("date")) {
                date = (String) params.get("date");
                flag2 = true;
            }
            if(flag1 && flag2)
                return getBaseMapper().findAnimesByCateAndDateAndName(cateId, date, name, new Page<>(cur,size));
            else if(flag1 && (!flag2))
                return getBaseMapper().findAnimesByCateAndName(cateId, name, new Page<>(cur,size));
            else if((!flag1) && flag2)
                return getBaseMapper().findAnimesByCateAndDate(cateId, date, new Page<>(cur,size));
            else
                return getBaseMapper().findAnimesByCate(cateId,new Page<>(cur,size));
        }
        else {
            LambdaQueryWrapper<Anime> lqw = new LambdaQueryWrapper<>();
            lqw.like(params.containsKey("name"), Anime::getName, params.get("name"));
            lqw.eq(params.containsKey("date"), Anime::getAniInitialDate, params.get("date"));
            return animeBaseMapper.selectPage(new Page<>(cur, size), lqw);
        }

    }

    @Override
    public Long addNewAnime(Anime anime) {
        System.out.println("21312");
        int res = animeBaseMapper.insert(anime);

        if(res > 0) {
            return anime.getId();
        }
        else {

            return null;
        }
    }

    @Override
    public boolean updateAnime(Long id, Anime anime) {
        anime.setId(id);
        int status = animeBaseMapper.updateById(anime);
        return status > 0;
    }

    @Override
    public boolean delAnime(Long id) {
        int status = animeBaseMapper.deleteById(id);
        return status > 0;
    }
}
