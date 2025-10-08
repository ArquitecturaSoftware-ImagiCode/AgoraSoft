package com.imagicode.agorasoft.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ClerkAuthFilter> clerkAuthFilterRegistration(ClerkAuthFilter filter) {
        FilterRegistrationBean<ClerkAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/api/usuario", "/api/protegida/*"); // solo estas rutas requieren Clerk
        registration.setOrder(1);
        return registration;
    }
}
