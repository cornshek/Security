package com.pd.pandaconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author GTY
 * 配置中心
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class PandaConfigserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(PandaConfigserverApplication.class, args);
    }

}
