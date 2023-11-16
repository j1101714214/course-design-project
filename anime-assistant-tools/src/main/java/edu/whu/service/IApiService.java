package edu.whu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
public interface IApiService extends IService<Api> {
    Page<Api> listPage(Integer cur, Integer size);

    Page<Api> listPageBySource(String name, Integer cur, Integer size);

    List<Api>listApiBySource(String name);

    Api getApiById(Long id);

    Long addNewApi(Api api);

    Boolean updateApi(Long id, Api api);
}
