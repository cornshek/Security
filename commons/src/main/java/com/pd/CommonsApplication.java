package com.pd;

import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author GTY
 */
@SpringBootApplication
public class CommonsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonsApplication.class, args);
    }

  /*  @Bean
    public OracleKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }*/

}
