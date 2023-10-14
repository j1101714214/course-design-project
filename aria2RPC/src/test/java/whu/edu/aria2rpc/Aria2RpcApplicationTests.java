package whu.edu.aria2rpc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whu.edu.aria2rpc.service.IAria2Service;

@SpringBootTest
class Aria2RpcApplicationTests {

    @Autowired
    private IAria2Service IAria2Service;

    @Test
    @SneakyThrows
    void testRequestDownload() {
        IAria2Service.requestDownload("https://www.voidtools.com/Everything-1.4.1.1024.x64.zip","EveryThing",".\\res");
    }

    @Test
    @SneakyThrows
    void testRequestStatus() {
        IAria2Service.requestStatus("cf1ee37124da3793");
    }

}
