package com.btask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.bento", "com.btask"})
@EnableJpaRepositories(basePackages = {"com.btask.user"})
@EntityScan(basePackages = {"com.btask.user"})
@SpringBootApplication
public class UserApp {

	public static void main(final String[] args) {
		SpringApplication.run(UserApp.class, args);
	}

}
