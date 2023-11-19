package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.domain.Source;
import edu.whu.dao.SourceDao;
import edu.whu.service.ISourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
@Service
public class SourceServiceImpl extends ServiceImpl<SourceDao, Source> implements ISourceService {

    @Override
    public Page<Api> getApiPageBySource(Long id, Integer page, Integer size) {
        return getBaseMapper().getApiPageBySourceId(id, new Page<>(page,size));
    }

    @Override
    public List<Source> getAllSources() {
        LambdaQueryWrapper<Source> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Source::getId);
        return getBaseMapper().selectList(lqw);
    }

    @Override
    public Long addNewSource(Source source) {
        source.setId(null);
        int res = getBaseMapper().insert(source);
        if(res > 0) {
            return source.getId();
        }
        else  {
            return null;
        }
    }
}
