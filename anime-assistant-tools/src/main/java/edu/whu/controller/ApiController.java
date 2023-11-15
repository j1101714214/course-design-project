package edu.whu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.service.IApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public ResponseEntity<Page<Api>> listApiPage() {
        return ResponseEntity.ok().build();
    }
}

