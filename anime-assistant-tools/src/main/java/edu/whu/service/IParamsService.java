package edu.whu.service;

import edu.whu.domain.Params;
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
public interface IParamsService extends IService<Params> {

    List<Params> getParamsByApi(Long id);

    Long addNewParams(Params params);
}
