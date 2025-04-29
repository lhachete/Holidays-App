package com.rob.boot;

import com.rob.domain.models.repository.OrganizationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.rob")
@EnableJpaRepositories(basePackages = "com.rob.domain.models.repository")
@EntityScan(basePackages = "com.rob.models")
public class BootApplication implements CommandLineRunner {

    public final OrganizationRepository organizationRepository;

    public BootApplication(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.organizationRepository.findAll().forEach(organization -> {
            System.out.println("Organization: " + organization.toString());
        });
    }
}
