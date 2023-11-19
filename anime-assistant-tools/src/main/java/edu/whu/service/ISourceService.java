package edu.whu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.domain.Api;
import edu.whu.domain.Source;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
public interface ISourceService extends IService<Source> {

    Page<Api> getApiPageBySource(Long id, Integer page, Integer size);

    List<Source> getAllSources();

    Long addNewSource(Source source);
}
