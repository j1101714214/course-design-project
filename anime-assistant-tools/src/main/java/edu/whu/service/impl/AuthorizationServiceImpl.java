package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.whu.domain.Authorization;
import edu.whu.dao.AuthorizationDao;
import edu.whu.service.IAuthorizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
@Service
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationDao, Authorization> implements IAuthorizationService {

    @Override
    public String getApiToken(Long userId, Long sourceId) {
        LambdaQueryWrapper<Authorization> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Authorization::getUserId, userId).eq(Authorization::getSource, sourceId)
                .select(Authorization::getApiToken);
        Authorization res = getBaseMapper().selectOne(lqw);
        return res.getApiKey();
    }

    @Override
    public String getApiKey(Long userId, Long sourceId) {
        LambdaQueryWrapper<Authorization> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Authorization::getUserId, userId).eq(Authorization::getSource, sourceId)
                .select(Authorization::getApiKey);
        Authorization res = getBaseMapper().selectOne(lqw);
        return res.getApiKey();
    }

    @Override
    public Boolean addNewAuth(Authorization authorization) {
        return getBaseMapper().insert(authorization) > 0;
    }
}
