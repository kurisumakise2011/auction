package com.auction.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("index");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/auction").setViewName("auction");
        registry.addViewController("/item").setViewName("item");
        registry.addViewController("/bid").setViewName("bid");
        registry.addViewController("/admin").setViewName("admin");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
