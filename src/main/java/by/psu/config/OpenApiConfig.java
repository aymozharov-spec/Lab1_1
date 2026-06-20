package by.psu.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi(@Value("${spring.application.version:1.0.0}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("Tour Service API")
                        .version(version)
                        .description("API для управления экскурсиями"))
                .addServersItem(new Server().url("/"));
    }
}