package com.perfulandia.carritoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import lombok.Builder;

@Entity
@Table(name = "carrito_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa un ítem dentro de un carrito de compras")
public class CarritoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "ID único del ítem generado automáticamente",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "ID del producto relacionado en el catálogo de productos",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long productoId;

    @Schema(
            description = "Nombre del producto para mostrar en el carrito",
            example = "Laptop HP",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombreProducto;

    @Schema(
            description = "Cantidad del producto en el carrito",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer cantidad;

    @Schema(
            description = "Precio unitario del producto al momento de agregarlo al carrito",
            example = "999.99",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal precioUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    @Schema(
            description = "Carrito al que pertenece este ítem",
            hidden = true
    )
    private Carrito carrito;

    @Schema(
            description = "Calcula el subtotal multiplicando cantidad por precio unitario",
            example = "1999.98",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public BigDecimal getSubtotal() {
        if (precioUnitario == null || cantidad == null) {
            return BigDecimal.ZERO;
        }
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}