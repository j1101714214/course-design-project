package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.whu.domain.Params;
import edu.whu.dao.ParamsDao;
import edu.whu.service.IParamsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.http.ResponseEntity;
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
public class ParamsServiceImpl extends ServiceImpl<ParamsDao, Params> implements IParamsService {

    @Override
    public List<Params> getParamsByApi(Long id) {
        LambdaQueryWrapper<Params> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Params::getApiId, id);
        return getBaseMapper().selectList(lqw);
    }

    @Override
    public Long addNewParams(Params params) {
        params.setId(null);
        int res = getBaseMapper().insert(params);
        if(res > 0) {
            return params.getId();
        }
        else {
            return null;
        }
    }
}
