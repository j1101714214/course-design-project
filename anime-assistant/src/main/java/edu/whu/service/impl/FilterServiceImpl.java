package edu.whu.service.impl;

import edu.whu.domain.Filter;
import edu.whu.dao.FilterDao;
import edu.whu.service.IFilterService;
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
public class FilterServiceImpl extends ServiceImpl<FilterDao, Filter> implements IFilterService {

    @Override
    public Long addFilter(Filter filter) {
        int res = getBaseMapper().insert(filter);
        if (res > 0) {
            return filter.getId();
        }
        else {
            return null;
        }
    }

    @Override
    public boolean updateFilter(Long id, Filter filter) {
        filter.setId(id);
        int res = getBaseMapper().updateById(filter);
        return res > 0;
    }
}
