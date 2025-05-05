package com.rob.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanDebugger implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) {
        String[] allBeans = context.getBeanDefinitionNames();
        Arrays.sort(allBeans);
        System.out.println("=== BEANS REGISTRADOS POR SPRING ===");
        for (String beanName : allBeans) {
            System.out.println(beanName);
        }
    }
}