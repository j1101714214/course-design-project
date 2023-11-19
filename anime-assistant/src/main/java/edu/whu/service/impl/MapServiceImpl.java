package edu.whu.service.impl;

import edu.whu.domain.Map;
import edu.whu.dao.MapDao;
import edu.whu.service.IMapService;
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
public class MapServiceImpl extends ServiceImpl<MapDao, Map> implements IMapService {

}
