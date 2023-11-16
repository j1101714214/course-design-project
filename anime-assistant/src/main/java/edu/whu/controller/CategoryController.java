package edu.whu.controller;


import edu.whu.domain.Category;
import edu.whu.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * @since 2023-11-13
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;
    @ApiOperation("获得所有分类")
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @ApiOperation("添加分类")
    @PostMapping("/add")
    public ResponseEntity<Long> addNewCategory(@RequestBody Category category) {
        Long res = categoryService.addNewCategory(category);
        if(res == null) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(res);
        }
    }

    @ApiOperation("更新分类信息")
    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }
}

