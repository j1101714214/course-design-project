package edu.whu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Source;
import edu.whu.service.ISourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/page")
    public ResponseEntity<Page<Source>> findSourcesPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size", defaultValue = "5")){
        return ResponseEntity.ok();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Source>> findAllSources() {
        List<Source> sources =
    }

    @GetMapping("")
    public ResponseEntity<List<>> findEntity()
}

