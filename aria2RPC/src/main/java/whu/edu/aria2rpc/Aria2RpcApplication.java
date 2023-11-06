package whu.edu.aria2rpc;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableScheduling
@EnableKnife4j
@EnableSwagger2
public class Aria2RpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Aria2RpcApplication.class, args);
    }

}
