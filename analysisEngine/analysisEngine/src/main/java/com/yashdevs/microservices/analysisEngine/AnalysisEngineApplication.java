package com.yashdevs.microservices.analysisEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients 
public class AnalysisEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalysisEngineApplication.class, args);
	}

}
