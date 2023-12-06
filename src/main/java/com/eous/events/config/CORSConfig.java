package com.eous.events.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig {

    @Value("${frontend.base-uri}")
    private String FRONTEND_BASE_URL;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(FRONTEND_BASE_URL)
                        .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.PUT.name())
                        .allowedHeaders("*")
                        .exposedHeaders(HttpHeaders.CONTENT_TYPE);

                registry.addMapping("/sse/subscribe/**")
                        .allowedOrigins(FRONTEND_BASE_URL)
                        .allowedMethods(HttpMethod.GET.name())
                        .allowedOriginPatterns(FRONTEND_BASE_URL + "/**")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .exposedHeaders(HttpHeaders.CONTENT_TYPE);
            }
        };
    }
}
