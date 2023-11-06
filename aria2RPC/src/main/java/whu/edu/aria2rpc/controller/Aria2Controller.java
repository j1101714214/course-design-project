package whu.edu.aria2rpc.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.DownloadInfo;
import whu.edu.aria2rpc.entity.ReqRes;
import whu.edu.aria2rpc.entity.bo.Aria2DownloadBo;
import whu.edu.aria2rpc.service.IAria2Service;
import whu.edu.aria2rpc.service.IDownInfoService;

import java.util.List;

@RestController
@RequestMapping("/download/aria2")
public class Aria2Controller {

    @Autowired
    private IAria2Service Aria2Service;

    @Autowired
    private IDownInfoService DownInfoService;

    @PostMapping("/jsonrpc")
    public ResponseEntity<ReqRes> aria2Download(@RequestBody Aria2DownloadBo bo){
        ReqRes reqRes = Aria2Service.requestDownload(bo.getDownloadUrl(),bo.getTitle(),bo.getSaveDir());
        return ResponseEntity.ok(reqRes);
    }

    @GetMapping("/status")
    public ResponseEntity<String> aria2GetStatus(@RequestParam String GID){
        Aria2Enum res = DownInfoService.requestStatus(GID);
        return ResponseEntity.ok(res.toString());
    }

    @GetMapping("/list")
    public ResponseEntity<List<DownloadInfo>> listDownloadInfo(){

    }
}
