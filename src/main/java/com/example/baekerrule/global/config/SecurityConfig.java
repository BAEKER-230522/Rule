//package com.example.baekerrule.global.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .authorizeHttpRequests
//                        (
//                        auth -> auth
//                                .requestMatchers("/api/rule/v1/search/**", "/api/rule/v1/list/**")
//                                .permitAll()
//                                .anyRequest()
//                                .hasRole("ROLE_ADMIN")
//                        )
//                .exceptionHandling().and()
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
//                .build();
//    }
//}
