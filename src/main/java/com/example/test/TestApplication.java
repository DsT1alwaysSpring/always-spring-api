package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//port 출력 시 사용
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
    public CommandLineRunner run(ApplicationContext ctx, Environment environment) {
        return args -> {
            // Spring Boot에서 실행되는 포트를 출력
            String port = environment.getProperty("local.server.port");
            System.out.println("Application is running on port: " + port);
        };
    }
}
