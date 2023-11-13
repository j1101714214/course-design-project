package edu.whu.service.impl;

import edu.whu.domain.Anime;
import edu.whu.dao.AnimeDao;
import edu.whu.service.IAnimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
