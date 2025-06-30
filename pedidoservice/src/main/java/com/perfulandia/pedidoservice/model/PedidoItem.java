package com.perfulandia.pedidoservice.model;

import com.perfulandia.pedidoservice.controller.PedidoController;

import com.perfulandia.productservice.controller.ProductoController;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.stereotype.Component;

@Entity // Esto significa que representa una tabla en la base de datos
@Table(name = "pedido_items") // Nombre de la tabla en la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productoId; // ID del producto en 'productoservice'
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario; // Precio al momento de la compra

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public BigDecimal getSubtotal() {
        if (precioUnitario == null || cantidad == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    @Component
    public class PedidoItemAssembler implements RepresentationModelAssembler<PedidoItem, EntityModel<PedidoItem>> {
        @Override
        public EntityModel<PedidoItem> toModel(PedidoItem item) {
            return EntityModel.of(item,
                    linkTo(methodOn(PedidoController.class).buscarPedido(item.getPedido().getId())).withRel("pedido"),
                    linkTo(methodOn(ProductoController.class).buscarProducto(item.getProductoId())).withRel("producto")
            );
        }
    }
}