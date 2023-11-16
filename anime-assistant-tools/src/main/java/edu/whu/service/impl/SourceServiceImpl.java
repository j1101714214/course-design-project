package edu.whu.service.impl;

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
        return null;
    }

    @Override
    public List<Source> getAllSources() {
        return null;
    }

    @Override
    public Long addNewSource(Source source) {
        return null;
    }
}
