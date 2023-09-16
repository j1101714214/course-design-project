package edu.whu.controller;

import edu.whu.exception.CustomerException;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.model.user.vo.LoginAndRegisterVo;
import edu.whu.service.IXyUserService;
import edu.whu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Akihabara
 * @version 1.0
 * @description AuthenticationController: 用户登录和注册操作控制器, 用于用户校验
 * @date 2023/9/16 15:52
 */
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private IXyUserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated @RequestBody LoginAndRegisterVo loginAndRegisterVo) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginAndRegisterVo.getUsername(), loginAndRegisterVo.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginAndRegisterVo.getUsername());
            String token = JwtUtil.getToken(userDetails);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            throw new CustomerException(ExceptionEnum.UN_AUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<XyUser> register(@Validated @RequestBody LoginAndRegisterVo loginAndRegisterVo) {
        XyUser xyUser = userService.saveUser(loginAndRegisterVo);
        return ResponseEntity.ok(xyUser);
    }


}
