package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.dao.ApiDao;
import edu.whu.service.IApiService;
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
public class ApiServiceImpl extends ServiceImpl<ApiDao, Api> implements IApiService {

    @Override
    public Page<Api> listPage(Integer cur, Integer size) {
        LambdaQueryWrapper<Api> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Api::getId);
        return getBaseMapper().selectPage(new Page<>(cur,size), lqw);
    }

    @Override
    public Page<Api> listPageBySource(String name, Integer cur, Integer size) {
        return getBaseMapper().getPageBySource(name, new Page<>(cur,size));
    }

    @Override
    public List<Api> listApiBySource(String name) {
        return getBaseMapper().getListBySource(name);
    }

    @Override
    public Api getApiById(Long id) {
        return getBaseMapper().selectById(id);

    }

    @Override
    public Long addNewApi(Api api) {
        int res = getBaseMapper().insert(api);
        if(res > 0) {
            return api.getId();
        }
        else  {
            return null;
        }
    }
}
