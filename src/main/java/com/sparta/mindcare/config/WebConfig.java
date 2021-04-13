package com.sparta.mindcare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") // CORS를 적용할 URL 패턴 정의
                .allowedOrigins("*") // 허락할 Origin(IP)을 지정  ex) "http://localhost:8080", "http://localhost:8081"
                .allowedMethods("*") // 허락할 HTTP method를 지정  ex) "GET", "POST"
                .maxAge(3600); // pre-flight 리퀘스트를 캐싱 해두는 시간 지정. seconds 단위
    }
}
