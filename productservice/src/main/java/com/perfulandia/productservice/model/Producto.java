package com.perfulandia.productservice.model;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema; // Anotación para documentar modelos

/**
 * Entidad que representa un producto en el sistema.
 * Esta clase se mapea a la tabla de productos en la base de datos.
 */
@Entity
@Data // Genera getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // Genera constructor con todos los argumentos (Lombok)
@NoArgsConstructor // Genera constructor sin argumentos (Lombok)
@Builder // Patrón builder para creación de objetos (Lombok)
@Schema(description = "Representa un producto en el inventario del sistema") // Descripción general para Swagger
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "ID único del producto generado automáticamente",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY) // Indica que es de solo lectura
    private long id;

    @Schema(
            description = "Nombre descriptivo del producto",
            example = "Laptop HP EliteBook",
            requiredMode = Schema.RequiredMode.REQUIRED) // Indica que es obligatorio
    private String nombre;

    @Schema(
            description = "Precio unitario del producto en dólares",
            example = "999.99",
            minimum = "0") // Validación mínima
    private double precio;

    @Schema(
            description = "Cantidad disponible en inventario",
            example = "50",
            minimum = "0") // Validación mínima
    private int stock;
}