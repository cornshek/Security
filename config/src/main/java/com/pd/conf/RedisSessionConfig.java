package com.pd.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author GTY
 * session共享配置类
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

}
