package edu.whu.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.mapper.XyUserMapper;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyUserServiceImpl: IXyUserService接口实现类
 * @date 2023/9/16 15:49
 */
@Slf4j
@Service
public class XyUserServiceImpl extends ServiceImpl<XyUserMapper, XyUser> implements IXyUserService {
}
