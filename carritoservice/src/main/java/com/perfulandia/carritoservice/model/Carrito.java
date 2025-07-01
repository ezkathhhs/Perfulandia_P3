package com.perfulandia.carritoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un carrito de compras en el sistema")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "ID único del carrito generado automáticamente",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(
            description = "ID del usuario propietario del carrito",
            example = "101",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long usuarioId;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Schema(
            description = "Lista de ítems contenidos en el carrito",
            example = "[]"
    )
    private List<CarritoItem> items = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    @Schema(
            description = "Fecha y hora de creación del carrito",
            example = "2023-01-01T10:00:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Schema(
            description = "Fecha y hora de la última actualización del carrito",
            example = "2023-01-01T11:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime fechaActualizacion;

    @Schema(
            description = "Añade un nuevo ítem al carrito",
            hidden = true
    )
    public void addItem(CarritoItem item) {
        this.items.add(item);
        item.setCarrito(this);
    }

    @Schema(
            description = "Elimina un ítem del carrito por ID de producto",
            hidden = true
    )
    public void removeItemByProductoId(Long productoId) {
        this.items.removeIf(item -> item.getProductoId().equals(productoId));
    }

    @Schema(
            description = "Calcula el total del carrito sumando los subtotales de todos los ítems",
            example = "1999.98",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public BigDecimal getTotalCarrito() {
        return items.stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}