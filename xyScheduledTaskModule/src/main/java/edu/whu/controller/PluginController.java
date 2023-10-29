package edu.whu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.model.plugin.pojo.XyPlugin;
import edu.whu.service.IXyPluginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Akihabara
 * @version 1.0
 * @description PluginController: 插件管理
 * @date 2023/9/27 21:28
 */
@Api(value = "插件管理API")
@RestController
@RequestMapping("/plugin")
public class PluginController {
    @Autowired
    private IXyPluginService pluginService;

    @GetMapping("/list")
    public ResponseEntity<IPage<XyPlugin>> listAllAvailablePlugin(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(pluginService.queryPluginList(pageNum, pageSize));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加插件")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYS_ADMIN')")     // 非游客
    public ResponseEntity<String> addPlugin (@RequestParam("file") MultipartFile file, @RequestBody XyPlugin plugin) {
        Boolean ret = pluginService.addPlugin(file, plugin);
        if(!ret) {
            return ResponseEntity.badRequest().body("上传插件失败");
        }
        return ResponseEntity.ok("上传插件成功");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除插件")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYS_ADMIN')")     // 非游客
    public ResponseEntity<String> deletePlugin(@PathVariable("id") Long id) {
        Boolean ret = pluginService.deletePlugin(id);
        if(!ret) {
            return ResponseEntity.badRequest().body("上传插件失败");
        }
        return ResponseEntity.ok("上传插件成功");
    }
}
