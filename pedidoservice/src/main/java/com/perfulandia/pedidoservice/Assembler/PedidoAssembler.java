package com.perfulandia.pedidoservice.Assembler;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.controller.PedidoController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                //GET PEDIDO BY ID
                linkTo(methodOn(PedidoController.class).buscarPedido(pedido.getId())).withSelfRel(),
                //GET ALL
                linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("pedidos"),
                // GET PEDIDO BY USUARIO ID
                linkTo(methodOn(PedidoController.class).buscarPedidosByUsuarioId(pedido.getUsuarioId())).withRel("pedidos-usuario"),
                //POST
                linkTo(methodOn(PedidoController.class).crearPedido(null)).withRel("post"),
                //DELETE
                linkTo(methodOn(PedidoController.class).eliminarPedido(pedido.getId())).withRel("delete")
        );
    }
}
