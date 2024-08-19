package com.rljj.switchswitchauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SwitchswitchauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwitchswitchauthApplication.class, args);
	}
}
