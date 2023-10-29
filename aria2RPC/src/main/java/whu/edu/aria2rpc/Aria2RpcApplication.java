package whu.edu.aria2rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Aria2RpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Aria2RpcApplication.class, args);
    }

}
