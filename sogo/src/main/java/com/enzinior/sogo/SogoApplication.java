package com.enzinior.sogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
public class SogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SogoApplication.class, args);
	}

}
