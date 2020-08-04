package com.pd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 订单模块
 * 来自shek分支的测试
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.pd.mapper")
public class PandaIndentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PandaIndentApplication.class, args);
    }

}
