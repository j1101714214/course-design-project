package edu.whu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.service.IApiService;
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
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private IApiService apiService;

    @GetMapping("/listByPage")
    @ApiOperation("查看api页面")
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
    @ApiOperation("根据影视网站id查询接口")
    public ResponseEntity<List<Api>> listApiBySource(@RequestParam(name = "source")String name ) {
        return ResponseEntity.ok(apiService.listApiBySource(name));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询接口")
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
    @ApiOperation("添加新接口")
    public ResponseEntity<Long> addNewApi(@RequestBody Api api) {
        Long res = apiService.addNewApi(api);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(res);
        }
    }

    @PostMapping("/update/{id}")
    @ApiOperation("更新接口信息")
    public ResponseEntity<Boolean> updateApi(@PathVariable("id") Long id, @RequestBody Api api) {
        Boolean res = apiService.updateApi(id, api);
        return ResponseEntity.ok(res);
    }
}

