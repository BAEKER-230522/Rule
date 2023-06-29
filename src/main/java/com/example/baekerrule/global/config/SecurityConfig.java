//package com.example.baekerrule.global.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@EnableWebFluxSecurity
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
//
//        httpSecurity
//                .authorizeExchange(exchanges ->
//                        exchanges
//                                .pathMatchers("/", "/error", "/index").permitAll()
//                                .anyExchange().authenticated()
//                ).oauth2ResourceServer(oauth2 -> oauth2
//                        .opaqueToken(token -> token.introspectionUri(this.introspectionUri)
//                                .introspectionClientCredentials(this.clientId, this.clientSecret)));
//
//        return httpSecurity.build();
//    }
//}
