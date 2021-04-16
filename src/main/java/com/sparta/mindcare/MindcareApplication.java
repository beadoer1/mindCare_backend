package com.sparta.mindcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MindcareApplication {
    public static void main(String[] args) {
        SpringApplication.run(MindcareApplication.class, args);
    }
}
