package whu.edu.aria2rpc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whu.edu.aria2rpc.service.IAria2Service;
import whu.edu.aria2rpc.service.IDownInfoService;

@SpringBootTest(classes = Aria2RpcApplication.class)
class Aria2RpcApplicationTests {

    @Autowired
    private IAria2Service IAria2Service;

    @Autowired
    private IDownInfoService IDownInfoService;

    @Test
    void testRequestDownload() {
        IAria2Service.requestDownload("https://www.voidtools.com/Everything-1.4.1.1024.x64.zip","EveryThing",".\\res");
    }

    @Test
    void testRequestStatus() {
        IDownInfoService.requestStatus("cf1ee37124da3793");
    }

    @Test
    void testQueryPage(){
        System.out.println(IDownInfoService.queryInfoList(1,1,3).getTotal());
        System.out.println(IDownInfoService.queryInfoList(0,1,3).getTotal());
    }

    @Test
    void testQueryDetail(){
        IDownInfoService.queryDetail("28263e5ce0db3129");
    }

}
