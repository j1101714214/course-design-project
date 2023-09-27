package edu.whu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.job.vo.AddJobVo;
import edu.whu.service.IXyJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Akihabara
 * @version 1.0
 * @description JobController: 任务管理
 * @date 2023/9/20 20:28
 */
@Api(value = "任务管理API")
@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private IXyJobService jobService;
    @GetMapping("/list")
    @ApiOperation(value = "查询任务列表")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYS_ADMIN')")
    public ResponseEntity<IPage<XyJob>> queryJobList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(jobService.queryUserList(pageNum, pageSize));
    }

    @GetMapping("/list/{userId}")
    @ApiOperation(value = "查询当前用户的任务列表")
    public ResponseEntity<IPage<XyJob>> queryJobListByUser(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok(jobService.queryJobListByUser(pageNum, pageSize, userId));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加任务")
    @PreAuthorize("!hasRole('ROLE_GUEST')")     // 非游客
    public ResponseEntity<String> addJob(@Validated @RequestBody AddJobVo addJobVo) {
        Boolean ret = jobService.addJob(addJobVo);
        if(!ret) {
            return ResponseEntity.badRequest().body("保存任务信息失败");
        }
        return ResponseEntity.ok("保存任务信息成功");
    }

    @PutMapping("/update/{jobId}")
    @ApiOperation(value = "更新任务")
    @PreAuthorize("!hasRole('ROLE_GUEST')")     // 非游客
    public ResponseEntity<String> updateJob(@Validated @RequestBody AddJobVo addJobVo, @PathVariable("jobId") Long jobId) {
        Boolean ret = jobService.updateJob(addJobVo, jobId);
        if(!ret) {
            return ResponseEntity.badRequest().body("更新任务信息失败");
        }
        return ResponseEntity.ok("更新任务信息成功");
    }

    @DeleteMapping("/delete/{jobId}")
    @ApiOperation(value = "删除任务")
    @PreAuthorize("!hasRole('ROLE_GUEST')")     // 非游客
    public ResponseEntity<String> deleteJob(@PathVariable("jobId") Long jobId) {
        Boolean ret = jobService.deleteJob(jobId);
        if(!ret) {
            return ResponseEntity.badRequest().body("删除任务信息失败");
        }
        return ResponseEntity.ok("删除任务信息成功");
    }

    @GetMapping("/run/{jobId}")
    @ApiOperation(value = "立即执行一次")
    public ResponseEntity<String> run(@PathVariable("jobId") Long jobId) {
        Boolean ret = jobService.runOnce(jobId);
        if(!ret) {
            return ResponseEntity.badRequest().body("任务调用出错");
        }
        return ResponseEntity.ok("任务成功调用");
    }

    @GetMapping("/resume/{jobId}")
    @ApiOperation(value = "恢复任务")
    public ResponseEntity<String> resume(@PathVariable("jobId") Long jobId) {
        Boolean ret = jobService.resumeJob(jobId);
        if(!ret) {
            return ResponseEntity.badRequest().body("任务恢复调用出错");
        }
        return ResponseEntity.ok("任务恢复调用成功");
    }

    @GetMapping("/pause/{jobId}")
    @ApiOperation(value = "挂起任务")
    public ResponseEntity<String> pause(@PathVariable("jobId") Long jobId) {
        Boolean ret = jobService.pauseJob(jobId);
        if(!ret) {
            return ResponseEntity.badRequest().body("任务挂起出错");
        }
        return ResponseEntity.ok("任务挂起成功");
    }
}
