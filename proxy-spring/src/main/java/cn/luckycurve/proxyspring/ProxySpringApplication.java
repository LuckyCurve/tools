package cn.luckycurve.proxyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ProxySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxySpringApplication.class, args);
    }

}
