package com.perfulandia.carritoservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI carritoServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(appName + " API")
                        .version("1.0")
                        .description("API para la gestión de carritos de compra en Perfulandia. " +
                                "Permite a los usuarios gestionar sus carritos de compra, " +
                                "agregar/eliminar productos y modificar cantidades.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@perfulandia.com")
                                .url("https://www.perfulandia.com/dev"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor de desarrollo local"),
                        new Server()
                                .url("https://api.perfulandia.com/carrito")
                                .description("Servidor de producción")));
    }
}