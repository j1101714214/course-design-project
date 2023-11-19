package edu.whu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.mapper.XyMetadataMapper;
import edu.whu.mapper.XyUserMapper;
import edu.whu.model.metadata.pojo.XyMetadata;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyMetadataService;
import edu.whu.service.IXyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyMetadataServiceImpl: IXyMetadataService接口实现类
 * @date 2023/11/14 18:48
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class XyMetadataServiceImpl extends ServiceImpl<XyMetadataMapper, XyMetadata> implements IXyMetadataService {
    @Autowired
    private XyMetadataMapper metadataMapper;

    @Override
    public List<XyMetadata> getAllMetadata() {
        return metadataMapper.selectList(null);
    }
}
