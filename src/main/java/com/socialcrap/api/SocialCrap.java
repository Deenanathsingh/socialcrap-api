package com.socialcrap.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SocialCrap {

	public static void main(String[] args) {
		SpringApplication.run(SocialCrap.class, args);
	}
}
