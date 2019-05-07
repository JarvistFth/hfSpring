package com.example.hfspring.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyResConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:/home/jarvist/avatar/");
        registry.addResourceHandler("/relicsPhoto/**").addResourceLocations("file:/home/jarvist/relicsPhoto");
        super.addResourceHandlers(registry);
    }
}
