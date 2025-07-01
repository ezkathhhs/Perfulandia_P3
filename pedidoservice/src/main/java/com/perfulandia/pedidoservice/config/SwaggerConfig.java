package com.perfulandia.pedidoservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración personalizada para Swagger/OpenAPI.
 * Define la información general de la API que aparecerá en la documentación.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pedido Service API")
                        .version("1.0")
                        .description("API para el manejo de pedidos en el sistema Perfulandia. " +
                                "Permite crear, consultar, actualizar y eliminar pedidos, " +
                                "así como gestionar su estado.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@perfulandia.com")
                                .url("https://www.perfulandia.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .termsOfService("https://www.perfulandia.com/terms"));
    }
}