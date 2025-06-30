package com.perfulandia.pedidoservice.Assembler;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.controller.PedidoController;
import com.perfulandia.pedidoservice.model.PedidoItem;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoItemAssembler implements RepresentationModelAssembler<PedidoItem, EntityModel<PedidoItem>> {

    @Override
    public EntityModel<PedidoItem> toModel(PedidoItem item) {
        return EntityModel.of(item,
                linkTo(methodOn(PedidoController.class).buscarPedido(item.getPedido().getId())).withRel("pedido"),
                linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("pedido-items"),

                // Esto requiere un Feign Client, no un Controller directo
                Link.of("http://localhost:8084/api/productos/" + item.getProductoId(), "producto")
        );
    }
}