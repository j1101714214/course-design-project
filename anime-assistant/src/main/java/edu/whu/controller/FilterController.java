package edu.whu.controller;


import edu.whu.domain.Filter;
import edu.whu.service.IFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
@RestController
@RequestMapping("/filter")
public class FilterController {

    @Autowired
    private IFilterService filterService;

    @PostMapping("/add")
    public ResponseEntity<Long> addNewFilter(@RequestBody Filter filter) {
        Long id = filterService.addFilter(filter);
        if (id != null) {
            return ResponseEntity.ok(id);
        }
        else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateFilter(@PathVariable("id") Long id, @RequestBody Filter filter) {
        boolean status = filterService.updateFilter(id, filter);
        if(status) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}

