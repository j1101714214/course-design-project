package whu.edu.aria2rpc.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.ReqRes;
import whu.edu.aria2rpc.entity.bo.Aria2DownloadBo;
import whu.edu.aria2rpc.service.IAria2Service;

@RestController
@RequestMapping("/download/aria2")
public class Aria2Controller {

    @Autowired
    private IAria2Service Aria2Service;

    @PostMapping("/jsonrpc")
    @SneakyThrows
    public ResponseEntity<ReqRes> aria2Download(@RequestBody Aria2DownloadBo bo){
        ReqRes reqRes = Aria2Service.requestDownload(bo.getDownloadUrl(),bo.getTitle(),bo.getSaveDir());
        return ResponseEntity.ok(reqRes);
    }

    @GetMapping("/status")
    @SneakyThrows
    public ResponseEntity<String> aria2GetStatus(@RequestParam String GID){
        Aria2Enum res = Aria2Service.requestStatus(GID);
        return ResponseEntity.ok(res.toString());
    }
}
