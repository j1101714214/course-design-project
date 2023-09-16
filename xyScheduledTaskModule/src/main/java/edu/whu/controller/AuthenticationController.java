package edu.whu.controller;

import edu.whu.service.IXyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Akihabara
 * @version 1.0
 * @description AuthenticationController: 用户登录和注册操作控制器, 用于用户校验
 * @date 2023/9/16 15:52
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private IXyUserService userService;


}
