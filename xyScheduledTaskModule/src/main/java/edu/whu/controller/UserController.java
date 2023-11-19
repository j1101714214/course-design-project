package edu.whu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyPluginService;
import edu.whu.service.IXyUserService;
import edu.whu.utils.PluginUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description UserController: 用户管理
 * @date 2023/9/16 21:23
 */
@Api(value = "用户管理API")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IXyUserService userService;
    @Autowired
    private PluginUtils pluginUtils;

    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "userId", value = "用户ID", dataTypeClass = Long.class, required = true, paramType = "path")
    @DeleteMapping("{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYS_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        Boolean ret = userService.deleteUser(userId);
        if(ret) {
            return ResponseEntity.ok("删除用户成功");
        } else {
            return ResponseEntity.badRequest().body("删除用户失败");
        }
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping("{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") Long userId, @RequestBody XyUser user) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean ret = userService.updateUser(principal, userId, user);
        if(ret) {
            return ResponseEntity.ok("更新用户成功");
        } else {
            return ResponseEntity.badRequest().body("更新用户失败");
        }
    }

    @ApiOperation(value = "根据Id查询用户信息")
    @GetMapping("{userId}")
    public ResponseEntity<XyUser> queryUserById(@PathVariable("userId") Long userId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(userService.queryUserById(principal, userId));
    }

    @ApiOperation(value = "查询用户列表")
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYS_ADMIN')")
    public ResponseEntity<IPage<XyUser>> queryUserList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(userService.queryUserList(pageNum, pageSize));
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出登录")
    public ResponseEntity<String> logout() {
        pluginUtils.killAllPlugins();
        return ResponseEntity.ok("退出成功");
    }
}
