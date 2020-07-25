package com.pd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author GTY
 */
@SpringBootApplication
@MapperScan("com.pd.mapper")
@EnableEurekaClient
@EnableFeignClients
@EnableRedisHttpSession
public class PandaUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PandaUsersApplication.class, args);
    }

}
