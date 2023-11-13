package edu.whu.service.impl;

import edu.whu.domain.Api;
import edu.whu.dao.ApiDao;
import edu.whu.service.IApiService;
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
public class ApiServiceImpl extends ServiceImpl<ApiDao, Api> implements IApiService {

}
