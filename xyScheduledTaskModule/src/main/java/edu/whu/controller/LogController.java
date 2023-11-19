package edu.whu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.job.vo.AddJobVo;
import edu.whu.model.log.pojo.XyLog;
import edu.whu.service.IXyJobService;
import edu.whu.service.IXyLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Akihabara
 * @version 1.0
 * @description JobController: 日志管理
 * @date 2023/9/20 20:28
 */
@Api(value = "日志管理API")
@RestController
@CrossOrigin
@RequestMapping("/log")
public class LogController {
    @Autowired
    private IXyLogService logService;

    @GetMapping("/list")
    @ApiOperation(value = "查询异常日志")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYS_ADMIN')")
    public ResponseEntity<IPage<XyLog>> listLog(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(logService.queryLogByUserId(pageNum, pageSize, principal));
    }
}
