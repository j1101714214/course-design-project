package whu.edu.aria2rpc.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import whu.edu.aria2rpc.entity.bo.Aria2DownloadBo;
import whu.edu.aria2rpc.service.IAria2Service;

@RestController
@RequestMapping("/download/aria2")
public class Aria2Controller {

    @Autowired
    private IAria2Service Aria2Service;

    @PostMapping("/jsonrpc")
    @SneakyThrows
    public ResponseEntity<String> aria2Download(@RequestBody Aria2DownloadBo bo){
        Aria2Service.requestDownload(bo.getDownloadUrl(),bo.getTitle(),bo.getSaveDir());
        return ResponseEntity.ok().build();
    }
}
