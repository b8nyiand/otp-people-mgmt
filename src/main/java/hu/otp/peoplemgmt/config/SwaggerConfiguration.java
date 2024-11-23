package hu.otp.peoplemgmt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OTP People Management API")
                        .version("0.0.1")
                        .description("API documentation for OTP People Management API. This is a " +
                                "home assignment for my OTP Java developer job application - " +
                                "a people administration and management application developed " +
                                "in Spring Boot. "));
    }

}
