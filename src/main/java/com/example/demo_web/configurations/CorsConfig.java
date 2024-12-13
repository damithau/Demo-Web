package com.example.demo_web.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Allow all requests to API endpoints
                .allowedOrigins("http://localhost:4200") // Allow requests from the frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                .allowCredentials(true) // Allow credentials (cookies, authorization headers)
                .allowedHeaders("*"); // Allow all headers
    }
}

