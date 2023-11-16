package edu.whu.controller;


import edu.whu.domain.Params;
import edu.whu.service.IParamsService;
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
@RequestMapping("/params")
public class ParamsController {
    @Autowired
    private IParamsService paramsService;

    @GetMapping("/list")
    public ResponseEntity<List<Params>> getApiParams(@RequestParam(name = "api") Long id) {
        return ResponseEntity.ok(paramsService.getParamsByApi(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addNewParams(@RequestBody Params params) {
        Long res = paramsService.addNewParams(params);
        if(res != null) {
            return ResponseEntity.ok(res);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}

