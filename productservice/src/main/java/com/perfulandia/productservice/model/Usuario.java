package com.perfulandia.productservice.model;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Objeto de Transferencia de Datos (DTO) que representa un usuario.
 * Este modelo simula la respuesta del microservicio de usuarios.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa un usuario en el sistema") // Descripción general para Swagger
public class Usuario {

    @Schema(
            description = "ID único del usuario",
            example = "101",
            accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @Schema(
            description = "Nombre completo del usuario",
            example = "Juan Pérez",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "juan.perez@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @Schema(
            description = "Rol del usuario en el sistema",
            example = "ADMIN",
            allowableValues = {"ADMIN", "USER", "GUEST"}) // Valores permitidos
    private String rol;
}