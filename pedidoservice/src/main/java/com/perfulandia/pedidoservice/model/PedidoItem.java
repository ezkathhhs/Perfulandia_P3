package com.perfulandia.pedidoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * Entidad que representa un item de un pedido.
 * Documentada con anotaciones Swagger para la documentación de la API.
 */
@Schema(description = "Item que forma parte de un pedido")
@Entity
@Table(name = "pedido_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {
    @Schema(description = "ID único del item", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del producto en el servicio de productos", example = "456", required = true)
    private Long productoId;

    @Schema(description = "Nombre del producto al momento de crear el pedido", example = "Smartphone XYZ")
    private String nombreProducto;

    @Schema(description = "Cantidad del producto en el pedido", example = "2", required = true)
    private Integer cantidad;

    @Schema(description = "Precio unitario del producto al momento de crear el pedido",
            example = "499.99", required = true)
    private BigDecimal precioUnitario;

    @Schema(description = "Pedido al que pertenece este item")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    /**
     * Calcula el subtotal del item (precio unitario * cantidad).
     * @return Subtotal del item o cero si falta información
     */
    public BigDecimal getSubtotal() {
        if (precioUnitario == null || cantidad == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}