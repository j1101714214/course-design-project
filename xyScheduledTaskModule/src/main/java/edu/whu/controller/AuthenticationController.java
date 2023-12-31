package edu.whu.controller;

import cn.hutool.log.Log;
import edu.whu.exception.CustomerException;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.model.user.vo.LoginAndRegisterVo;
import edu.whu.model.user.vo.LoginResponse;
import edu.whu.service.IXyJobService;
import edu.whu.service.IXyPluginService;
import edu.whu.service.IXyUserService;
import edu.whu.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Akihabara
 * @version 1.0
 * @description AuthenticationController: 用户登录和注册操作控制器, 用于用户校验
 * @date 2023/9/16 15:52
 */
@Api(value = "用户登录与注册控制器")
@RestController
@RequestMapping("/authenticate")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private IXyUserService userService;
    @Autowired
    private IXyJobService jobService;
    @Autowired
    private IXyPluginService pluginService;

    @ApiOperation(value = "登录操作")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginAndRegisterVo loginAndRegisterVo) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginAndRegisterVo.getUsername(), loginAndRegisterVo.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginAndRegisterVo.getUsername());
            String token = JwtUtil.getToken(userDetails);
            // 用户如果成功登录, 就针对用户的所有任务投入启动
            XyUser operator = userService.findUserByUsername(userDetails.getUsername(), false);
            jobService.startTasksByUserId(operator.getId());
            // 启动插件
            pluginService.loadPluginsByUserId(operator);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setUserLevel(operator.getUserLevel());
            loginResponse.setUserId(String.valueOf(operator.getId()));
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            throw new CustomerException(ExceptionEnum.UN_AUTHORIZED);
        }
    }

    @ApiOperation(value = "注册操作")
    @PostMapping("/register")
    public ResponseEntity<XyUser> register(@Validated @RequestBody LoginAndRegisterVo loginAndRegisterVo) {
        XyUser xyUser = userService.saveUser(loginAndRegisterVo);
        return ResponseEntity.ok(xyUser);
    }


}
