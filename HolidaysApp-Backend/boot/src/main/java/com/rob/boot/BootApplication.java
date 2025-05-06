package com.rob.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = "com.rob.main.driven.repositories")
@EntityScan(basePackages = "com.rob.main.driven.repositories.models")
@ComponentScan(basePackages = {
        "com.rob.driving.controllers",
        "com.rob.driving.mappers",
        "com.rob.driving.dtos",
        "com.rob.driving.api",
        "com.rob.main.driven.repositories",
        "com.rob.application",
        "com.rob.main.driven.repositories.mappers"
})
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
