package com.gordon.iRecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IRecipeApplication {

    public static void main(String[] args) {
        SpringApplication.run(IRecipeApplication.class, args);
    }

}
