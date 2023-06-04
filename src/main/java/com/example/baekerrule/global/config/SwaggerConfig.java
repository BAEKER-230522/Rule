package com.example.baekerrule.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI baekerRuleAPI() {
        return new OpenAPI()
                .info(new Info().title("Baeker-Rule")
                        .description("Baeker 규칙의 API 명세서 입니다.")
                        .version("1.0"));
    }
}
