package com.ping.simpledirectoryapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public ClientTypeInterceptor clientTypeInterceptor;

    public ThrottleInterceptor throttleInterceptor;

    public WebConfig(ClientTypeInterceptor clientTypeInterceptor, ThrottleInterceptor throttleInterceptor) {
        this.clientTypeInterceptor = clientTypeInterceptor;
        this.throttleInterceptor = throttleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientTypeInterceptor).addPathPatterns("/environments/**");
        registry.addInterceptor(throttleInterceptor).addPathPatterns("/environments/**");
    }
}
