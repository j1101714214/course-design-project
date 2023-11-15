package edu.whu;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.whu.domain.Anime;
import edu.whu.service.IAnimeService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AnimeTest {
    @Autowired
    private IAnimeService service;
    @BeforeEach
    public void beforeOp() {
        Anime anime = new Anime();
        anime.setName("test1");
        anime.setDescription("this is an insert test");
        anime.setAniSeason(1);
        anime.setAniTotalEpisodes(23);
    }
    @Test
    public void testInsert() {
        Assertions.assertNotNull(service);
        Anime anime = new Anime();
        anime.setName("test1");
        anime.setDescription("this is an insert test");
        anime.setAniSeason(1);
        anime.setAniTotalEpisodes(23);

        Long res = service.addNewAnime(anime);

        Assertions.assertNotNull(res);
    }

    @Test
    public void testUpdate() {
        Anime anime = new Anime();
        anime.setName("test");
        anime.setDescription("this is an update test");
        anime.setAniSeason(1);
        anime.setAniTotalEpisodes(23);
        Long res = service.addNewAnime(anime);
        Assertions.assertNotNull(res);

        anime.setName("update test");
        boolean status = service.updateAnime(res, anime);
        Assertions.assertTrue(status);

        Anime anime1 = service.findAnimeById(res);
        Assertions.assertEquals(anime1.getName(), "update test");
    }

    @Test
    public void testSelectById() {
        Anime anime = service.findAnimeById(2L);
        Assertions.assertNotNull(anime);
    }
    @Test
    public void testDel() {
        Anime anime = service.findAnimeById(2L);
        Assertions.assertNotNull(anime);
        service.delAnime(anime.getId());
        Anime anime1 = service.findAnimeById(2L);
        Assertions.assertNull(anime1);
    }

    @Test
    public void testSelect() {
        Map<String, Object> params = new HashMap<>();
        params.put("cate", 23);
        Page<Anime> animePage = service.findAnimePageByMap(params, 0, 5);
        Assertions.assertNotNull(animePage);
        Assertions.assertEquals(animePage.getSize(), 5);
    }

    @Test
    public void testSelect2() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "test");
        Page<Anime> animePage = service.findAnimePageByMap(params, 0, 5);
        Assertions.assertNotNull(animePage);
        Assertions.assertEquals(animePage.getSize(), 5);
    }
}