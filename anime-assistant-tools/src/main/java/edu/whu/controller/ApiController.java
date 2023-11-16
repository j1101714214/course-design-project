package edu.whu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.service.IApiService;
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
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private IApiService apiService;

    @GetMapping("/listByPage")
    public ResponseEntity<Page<Api>> listApiPage(@RequestParam(name = "source", required = false) String name,
                                                 @RequestParam(name = "page", defaultValue = "0") Integer cur,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<Api> res = null;
        if(name == null) res = apiService.listPage(cur, size);
        else res = apiService.listPageBySource(name, cur, size);

        if(res != null) {
            return ResponseEntity.ok(res);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Api>> listApiBySource(@RequestParam(name = "source")String name ) {
        return ResponseEntity.ok(apiService.listApiBySource(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Api> getApiInfo(@PathVariable("id") Long id) {
        Api res = apiService.getApiById(id);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(res);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addNewApi(@RequestBody Api api) {
        Long res = apiService.addNewApi(api);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(res);
        }
    }
}

