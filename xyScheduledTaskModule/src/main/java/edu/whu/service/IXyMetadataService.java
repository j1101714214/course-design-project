package edu.whu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.model.metadata.pojo.XyMetadata;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.model.user.vo.LoginAndRegisterVo;

import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description IXyUserService: XyMetadata对应的服务层接口
 * @date 2023/9/16 15:49
 */
public interface IXyMetadataService extends IService<XyMetadata> {
    List<XyMetadata> getAllMetadata();
}
