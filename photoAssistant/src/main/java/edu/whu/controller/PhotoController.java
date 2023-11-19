package edu.whu.controller;

import edu.whu.model.pojo.PhotoMetadata;
import edu.whu.service.IXyPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Akihabara
 * @version 1.0
 * @description PhotoController: 图片管理插件Controller
 * @date 2023/11/12 19:36
 */
@Api(value = "图片管理插件API")
@RestController
@RequestMapping("/photo")
@CrossOrigin
public class PhotoController {
    @Autowired
    private IXyPhotoService photoService;

    private static final ExecutorService SINGLE_THREAD = Executors.newSingleThreadExecutor();

    @ApiOperation(value = "加载文件操作")
    @GetMapping("/load")
    public ResponseEntity<String> loadFiles(@RequestParam(value = "root") String root, @RequestParam(value = "userId") Long userId) {
        SINGLE_THREAD.execute(() -> photoService.loadFiles(root, userId));
        return ResponseEntity.ok("任务调用成功");
    }

    @ApiOperation(value = "加载文件操作")
    @GetMapping("/classify")
    public ResponseEntity<String> classify(@RequestParam(value = "userId") Long userId) {
        SINGLE_THREAD.execute(() -> photoService.classify(userId));
        return ResponseEntity.ok("任务调用成功");
    }

    @ApiOperation(value = "获取分类图片")
    @GetMapping("/list/{criteria}/{userId}")
    public ResponseEntity<Map<String, List<PhotoMetadata>>> list(@PathVariable String criteria, @PathVariable Long userId) {
        return ResponseEntity.ok(photoService.listByCriteria(criteria, userId));
    }
}
