package edu.whu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.model.plugin.pojo.XyPlugin;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Akihabara
 * @version 1.0
 * @description IXyPluginService: XyPlugin对应的服务层接口
 * @date 2023/9/27 21:26
 */
public interface IXyPluginService extends IService<XyPlugin> {
    /**
     * 获取当前可用的插件分页列表
     * @param pageNum   页码
     * @param pageSize  每页条数
     * @return          当前页面的插件条数
     */
    IPage<XyPlugin> queryPluginList(Integer pageNum, Integer pageSize);

    /**
     * 仅管理员可使用: 上传插件到云服务器或者本地
     * @param file      文件
     * @param plugin    插件详情
     * @return          操作结果
     */
    Boolean addPlugin(MultipartFile file, XyPlugin plugin);

    /**
     * 仅管理员可使用: 删除插件
     * @param id        对应插件id
     * @return          操作结果
     */
    Boolean deletePlugin(Long id);
}
