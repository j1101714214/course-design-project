package edu.whu.controller;

import edu.whu.model.metadata.pojo.XyMetadata;
import edu.whu.service.IXyMetadataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description MetadataController: XyMetadataService对应控制器
 * @date 2023/11/14 18:50
 */
@Api(value = "元数据管理API")
@RestController
@CrossOrigin
@RequestMapping("/metadata")
public class MetadataController {
    @Autowired
    private IXyMetadataService metadataService;

    @GetMapping("/list")
    @ApiOperation(value = "获取所有任务元数据")
    public ResponseEntity<List<XyMetadata>> list() {
        return ResponseEntity.ok(metadataService.getAllMetadata());
    }
}
