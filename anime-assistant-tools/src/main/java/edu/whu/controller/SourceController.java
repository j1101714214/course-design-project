package edu.whu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.domain.Source;
import edu.whu.service.ISourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
@RestController
@RequestMapping("/source")
public class SourceController {

    @Autowired
    private ISourceService sourceService;
    @GetMapping("/page/{id}")
    @ApiOperation("查看影视源的id")
    public ResponseEntity<Page<Api>> findSourcesPage(@PathVariable("id")Long id,
                                                     @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(sourceService.getApiPageBySource(id, page,size));
    }

    @GetMapping("/all")
    @ApiOperation("查看所有影视源")
    public ResponseEntity<List<Source>> findAllSources() {
        return ResponseEntity.ok(sourceService.getAllSources());
    }

    @PostMapping("/add")
    @ApiOperation("添加新影视源")
    public ResponseEntity<Long> addNewSource(@RequestBody Source source) {
        Long res = sourceService.addNewSource(source);
        if(res != null) {
            return ResponseEntity.ok(res);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }


}

