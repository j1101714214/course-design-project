package edu.whu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.domain.Source;
import edu.whu.service.ISourceService;
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
    public ResponseEntity<Page<Api>> findSourcesPage(@PathVariable("id")Long id,
                                                     @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(sourceService.getApiPageBySource(id, page,size));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Source>> findAllSources() {
        return ResponseEntity.ok(sourceService.getAllSources());
    }

    @PostMapping("/add")
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

