package com.pd;

import com.pd.interceptor.FeignInterceptor;
import jdk.nashorn.internal.runtime.GlobalConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 页面项目
 * @author GTY
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@EnableCircuitBreaker
public class PandaHtmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(PandaHtmlApplication.class, args);
    }
}
