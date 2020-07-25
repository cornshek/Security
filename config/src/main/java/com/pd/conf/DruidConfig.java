package com.pd.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.alibaba.druid.pool.DruidDataSource;


/**
 * 自定义DataSource配置
 * @author GTY
 */
@Configuration
public class DruidConfig {
    @Autowired
    /**springBoot的运行环境类, 获取到springBoot配置文件中内容*/
    private Environment environment;

    /**配置一个DataSource*/
    @Bean
    public DataSource createDataSource() { // 创建一个Druid的数据源 DruidDataSource
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        druidDataSource.setUrl(environment.getProperty("spring.datasource.url"));
        druidDataSource.setUsername(environment.getProperty("spring.datasource.username"));
        druidDataSource.setPassword(environment.getProperty("spring.datasource.password"));
        /**初始连接数*/
        druidDataSource.setInitialSize(Integer.parseInt(environment.getProperty("spring.datasource.druid.initSize")));
        /**最大连接数*/
        druidDataSource.setMaxActive(Integer.parseInt(environment.getProperty("spring.datasource.druid.maxSize")));
        /**最小连接数*/
        druidDataSource.setMinIdle(Integer.parseInt(environment.getProperty("spring.datasource.druid.minSize")));
        return druidDataSource;
    }

}
