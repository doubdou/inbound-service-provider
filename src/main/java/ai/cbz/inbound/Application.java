package ai.cbz.inbound;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Jinzw
 * @Date: 2020/11/16 15:21
 */
//@DubboComponentScan
@EnableDubbo
@SpringBootApplication
@ComponentScan(basePackages = {"ai.cbz.inbound"})
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
