package edu.whu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.model.plugin.pojo.XyPlugin;
import edu.whu.service.IXyPluginService;
import io.minio.MinioClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@CrossOrigin
@RequestMapping("/plugin")
public class PluginController {
    @Autowired
    private IXyPluginService pluginService;



    @GetMapping("/list")
    @ApiOperation(value = "查询所有可用插件")
    public ResponseEntity<IPage<XyPlugin>> listAllAvailablePlugin(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(pluginService.queryPluginList(pageNum, pageSize));
    }

    @GetMapping("/list/{userId}")
    @ApiOperation(value = "查询所有可用插件")
    public ResponseEntity<IPage<XyPlugin>> listAllAvailablePlugin(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(pluginService.queryPluginListByUser(pageNum, pageSize, userId));
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "添加插件")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYS_ADMIN')")     // 非游客
    public ResponseEntity<String> addPlugin (@RequestParam(value = "file") MultipartFile file, XyPlugin plugin) {
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

    @PostMapping("/download")
    @ApiOperation(value = "下载插件")
    public ResponseEntity<String> downloadPlugin(@RequestBody XyPlugin xyPlugin) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean ret = pluginService.downloadPluginAndStart(principal, xyPlugin.getId());
        if(!ret) {
            return ResponseEntity.badRequest().body("下载插件失败");
        }
        return ResponseEntity.ok("下载插件成功");
    }

    @PostMapping("/stop")
    @ApiOperation(value = "停止插件")
    public ResponseEntity<String> stopPlugin(@RequestBody XyPlugin xyPlugin) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean ret = pluginService.stopPlugin(xyPlugin);
        if(!ret) {
            return ResponseEntity.badRequest().body("停止插件失败");
        }
        return ResponseEntity.ok("停止插件成功");
    }

    @PostMapping("/start")
    @ApiOperation(value = "启动插件")
    public ResponseEntity<String> startPlugin(@RequestBody XyPlugin xyPlugin) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean ret = pluginService.startPlugin(xyPlugin);
        if(!ret) {
            return ResponseEntity.badRequest().body("启动插件失败");
        }
        return ResponseEntity.ok("启动插件成功");
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除插件")
    public ResponseEntity<String> deletePlugin(@RequestBody XyPlugin xyPlugin) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean ret = pluginService.removePlugin(principal, xyPlugin);
        if(!ret) {
            return ResponseEntity.badRequest().body("删除插件失败");
        }
        return ResponseEntity.ok("删除插件成功");
    }
}
