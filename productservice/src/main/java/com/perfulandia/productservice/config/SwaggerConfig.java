package com.perfulandia.productservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración personalizada para la documentación de la API usando OpenAPI 3.0.
 * Esta clase define metadatos generales sobre la API que se mostrarán en la UI de Swagger.
 */
@Configuration // Indica que esta clase es una configuración de Spring
public class SwaggerConfig {

    /**
     * Define la configuración personalizada para OpenAPI.
     * @return Objeto OpenAPI con la configuración de metadatos de la API
     */
    @Bean // Indica que este método produce un bean gestionado por Spring
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Service API") // Título de la API
                        .version("1.0") // Versión de la API
                        .description("API para el manejo de productos en Perfulandia") // Descripción
                        .license(new License() // Información de licencia
                                .name("Apache 2.0") // Tipo de licencia
                                .url("http://springdoc.org"))); // URL de referencia
    }
}