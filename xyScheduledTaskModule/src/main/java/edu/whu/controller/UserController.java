package edu.whu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Akihabara
 * @version 1.0
 * @description UserController: 用户管理
 * @date 2023/9/16 21:23
 */
@Api(value = "用户管理API")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IXyUserService userService;

    @ApiOperation(value = "删除用户")
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
        Boolean ret = userService.updateUser(userId, user);
        if(ret) {
            return ResponseEntity.ok("更新用户成功");
        } else {
            return ResponseEntity.badRequest().body("更新用户失败");
        }
    }

    @ApiOperation(value = "根据Id查询用户信息")
    @GetMapping("{userId}")
    public ResponseEntity<XyUser> queryUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.queryUserById(userId));
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
}