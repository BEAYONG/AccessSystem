package com.nubomed.AccessSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableScheduling
@EnableWebFlux
@SpringBootApplication
public class AccessSystemApplication implements WebFluxConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(AccessSystemApplication.class, args);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

}