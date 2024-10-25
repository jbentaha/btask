package com.bento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.bento")
@SpringBootApplication
public class UserApp {

	public static void main(final String[] args) {
		SpringApplication.run(UserApp.class, args);
	}

}
