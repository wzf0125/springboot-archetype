package com.quanta.archetype.config;

import com.quanta.archetype.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description 拦截器配置
 * @author Leslie Leung
 * @date 2021/9/24
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                // 放通静态资源和所有登录接口
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/student/login")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/common/helloWorld")
                .excludePathPatterns("/common/forgetPassword")
                .excludePathPatterns("/common/sendForgetPasswordCode")
                .addPathPatterns("/**");

    }
}
