package edu.whu.service;

import edu.whu.domain.Filter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
public interface IFilterService extends IService<Filter> {

    Long addFilter(Filter filter);

    boolean updateFilter(Long id, Filter filter);
}
