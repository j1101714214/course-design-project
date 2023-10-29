package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.exception.CustomerException;
import edu.whu.mapper.XyPluginMapper;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.plugin.pojo.XyPlugin;
import edu.whu.service.IXyPluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyPluginServiceImpl: IXyPluginService接口实现类
 * @date 2023/9/27 21:27
 */
@Service
public class XyPluginServiceImpl extends ServiceImpl<XyPluginMapper, XyPlugin> implements IXyPluginService {
    @Autowired
    private XyPluginMapper pluginMapper;
    @Override
    public IPage<XyPlugin> queryPluginList(Integer pageNum, Integer pageSize) {
        IPage<XyPlugin> page = new Page<>(pageNum - 1, pageSize);
        return pluginMapper.selectPage(page, null);
    }

    @Override
    public Boolean addPlugin(MultipartFile file, XyPlugin plugin) {
        String filename = file.getOriginalFilename();
        if(filename == null || !filename.endsWith(".jar")) {
            throw new CustomerException(ExceptionEnum.ERROR_PLUGIN_TYPE);
        }

        // TODO: 上传文件到对应的与服务器并且可以让用户下载

        // 保存插件信息
        int cnt = pluginMapper.insert(plugin);
        return cnt == 1;
    }

    @Override
    public Boolean deletePlugin(Long id) {
        XyPlugin plugin = pluginMapper.selectById(id);

        // TODO: 删除远程服务器的插件文件
        int cnt = pluginMapper.deleteById(id);
        return cnt == 1;
    }
}
