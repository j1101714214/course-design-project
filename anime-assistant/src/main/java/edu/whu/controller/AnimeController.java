package edu.whu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Anime;
import edu.whu.service.IAnimeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private IAnimeService animeService;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id获取Anime全部信息")
    public ResponseEntity<Anime> getAnime(@PathVariable(name = "id") Long id) {
        Anime res = animeService.findAnimeById(id);
        System.out.println(res == null);
        if (res != null) {
            return ResponseEntity.ok(res);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addNewAnime(@RequestBody Anime anime) {
        Long id = animeService.addNewAnime(anime);
        if (id != null) {
            return ResponseEntity.ok(id);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update/{id}")
    @ApiOperation("更新视频信息")
    public ResponseEntity<Boolean> updateAnime(@PathVariable("id") Long id, @RequestBody Anime anime) {
        boolean status = animeService.updateAnime(id, anime);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/del/{id}")
    @ApiOperation("删除视频元信息")
    public ResponseEntity<Boolean> deleteAnime(@PathVariable("id") Long id) {
        boolean status = animeService.delAnime(id);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/test")
    public String test() {
        return "hello world!";
    }

    @GetMapping("/list")
    @ApiOperation("根据查询条件获得页面")
    public ResponseEntity<IPage<Anime>> getAnimePages(@ApiParam("分类信息") @RequestParam(name = "cate", required = false) Long id,
                                                      @ApiParam("视频名称") @RequestParam(name = "name", required = false) String name,
                                                      @ApiParam("发布时间(YYYY-mm)") @RequestParam(name = "date", required = false) String date,
                                                      @ApiParam("页号") @RequestParam(name = "page", defaultValue = "0") Integer cur,
                                                      @ApiParam("页面大小") @RequestParam(name = "size", defaultValue = "10") Integer size)  {
        Map<String, Object> params = new HashMap<>();
        if (id != null) {
           params.put("cate", id);
        }
        if (name != null) {
            params.put("name", name);
        }
        if (date != null) {
            params.put("date", date);
        }
        Page<Anime> res = animeService.findAnimePageByMap(params, cur, size);

        return ResponseEntity.ok(res);

    }


}

