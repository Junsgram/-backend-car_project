package com.pratice.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PracticeCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeCarApplication.class, args);
    }

}
