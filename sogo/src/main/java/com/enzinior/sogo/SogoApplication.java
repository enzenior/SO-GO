package com.enzinior.sogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SogoApplication.class, args);
	}

}
