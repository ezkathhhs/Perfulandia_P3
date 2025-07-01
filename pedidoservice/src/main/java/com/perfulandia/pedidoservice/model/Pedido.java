package com.perfulandia.pedidoservice.model;

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

/**
 * Entidad que representa un pedido en el sistema.
 * Documentada con anotaciones Swagger para la documentación de la API.
 */
@Schema(description = "Entidad que representa un pedido en el sistema")
@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Schema(description = "ID único del pedido", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del usuario que realizó el pedido", example = "123", required = true)
    @Column(nullable = false)
    private Long usuarioId;

    @Schema(description = "Items que componen el pedido")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PedidoItem> items = new ArrayList<>();

    @Schema(description = "Estado actual del pedido", example = "PENDIENTE", required = true)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @Schema(description = "Total del pedido calculado como suma de los subtotales de los items",
            example = "99.99", required = true)
    @Column(nullable = false)
    private BigDecimal totalPedido;

    @Schema(description = "Dirección de envío del pedido", example = "Calle Falsa 123", required = true)
    @Column(nullable = false)
    private String direccionEnvio;

    @Schema(description = "Ciudad de envío del pedido", example = "Madrid")
    private String ciudadEnvio;

    @Schema(description = "Código postal de envío", example = "28001")
    private String codigoPostalEnvio;

    @Schema(description = "Fecha de creación del pedido", example = "2023-01-01T10:00:00")
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de última actualización del pedido", example = "2023-01-01T10:30:00")
    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    /**
     * Agrega un item al pedido y establece la relación bidireccional.
     * @param item Item a agregar al pedido
     */
    public void addItem(PedidoItem item) {
        this.items.add(item);
        item.setPedido(this);
    }

    /**
     * Calcula el total del pedido sumando los subtotales de todos los items.
     */
    public void calcularTotalPedido() {
        this.totalPedido = items.stream()
                .map(PedidoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Callback ejecutado antes de persistir el pedido.
     * Establece el estado por defecto como PENDIENTE si no está definido.
     */
    @PrePersist
    protected void onCreate() {
        if (this.estado == null) this.estado = EstadoPedido.PENDIENTE;
    }
}