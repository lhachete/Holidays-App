package com.rob.boot;

import com.rob.domain.models.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.rob")
@EnableJpaRepositories(basePackages = "com.rob.domain.models.repository")
@EntityScan(basePackages = "com.rob.domain.models.entities")
public class BootApplication implements CommandLineRunner {

    public final RoleRepository roleRepository;

    public BootApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.roleRepository.findAll().forEach(role -> {
            System.out.println("Role: " + role.toString());
        });
    }
}
