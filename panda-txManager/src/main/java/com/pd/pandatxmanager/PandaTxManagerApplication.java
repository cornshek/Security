package com.pd.pandatxmanager;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableTransactionManagerServer
@EnableEurekaClient
public class PandaTxManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PandaTxManagerApplication.class, args);
	}

}
