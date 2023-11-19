package edu.whu.service;

import edu.whu.domain.Authorization;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
public interface IAuthorizationService extends IService<Authorization> {

    String getApiToken(Long userId, Long sourceId);

    String getApiKey(Long userId, Long sourceId);

   Boolean addNewAuth(Authorization authorization);
}
