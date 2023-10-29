package edu.whu.service.impl;

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
 * @since 2023-10-29
 */
@Service
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationDao, Authorization> implements IAuthorizationService {

}
