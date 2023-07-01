package com.hellparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HellpartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellpartyApplication.class, args);
	}

}
