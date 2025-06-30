package com.perfulandia.pedidoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perfulandia.pedidoservice.Assembler.PedidoItemAssembler;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PedidoItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @Column(nullable = false)
    private BigDecimal totalPedido;

    @Column(nullable = false)
    private String direccionEnvio;

    private String ciudadEnvio;
    private String codigoPostalEnvio;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    public void addItem(PedidoItem item) {
        this.items.add(item);
        item.setPedido(this);
    }

    public void calcularTotalPedido() {
        this.totalPedido = items.stream().map(PedidoItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    protected void onCreate() {
        if (this.estado == null) this.estado = EstadoPedido.PENDIENTE;
    }

    @JsonIgnore // Para evitar recursión
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)

    // Método para exponer items con HATEOAS
    public List<EntityModel<PedidoItem>> getItemsConEnlaces(PedidoItemAssembler assembler) {
        return items.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }
}