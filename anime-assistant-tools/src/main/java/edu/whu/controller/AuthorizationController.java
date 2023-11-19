package edu.whu.controller;


import edu.whu.domain.Authorization;
import edu.whu.service.IAuthorizationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
@RestController
@RequestMapping("/api-auth")
public class AuthorizationController {
    @Autowired
    public IAuthorizationService authorizationService;

    @PostMapping("/add")
    @ApiOperation("添加视频源的权限信息")
    public ResponseEntity<Boolean> addNewAuth(@RequestBody Authorization authorization) {
        return ResponseEntity.ok(authorizationService.addNewAuth(authorization));
    }

    @GetMapping("/api-key")
    @ApiOperation("获取api-key")
    public ResponseEntity<String> getApiKey(@RequestParam("userId") Long userId,
                                            @RequestParam("source") Long sourceId) {
        String res = authorizationService.getApiKey(userId, sourceId);
        if(res == null) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(res);
        }
    }

    @GetMapping("/api-token")
    @ApiOperation("获取api-token")
    public ResponseEntity<String> getApiToken(@RequestParam("userId") Long userId,
                                            @RequestParam("source") Long sourceId) {
        String res = authorizationService.getApiToken(userId, sourceId);
        if(res == null) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(res);
        }
    }
}

