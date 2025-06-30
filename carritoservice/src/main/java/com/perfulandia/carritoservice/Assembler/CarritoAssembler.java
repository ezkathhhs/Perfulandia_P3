package com.perfulandia.carritoservice.Assembler;

import com.perfulandia.carritoservice.controller.CarritoController;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.productservice.controller.ProductoController;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {
    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                // Self link (obtener carrito por ID)
                linkTo(methodOn(CarritoController.class).getCartById(carrito.getId())).withSelfRel(),

                // Obtener carrito por usuarioId
                linkTo(methodOn(CarritoController.class).getCarritoByUsuarioId(carrito.getUsuarioId())).withRel("por-usuario"),

                // Agregar item al carrito
                linkTo(methodOn(CarritoController.class).addItemToCart(carrito.getUsuarioId(), null)).withRel("agregar-item"),

                // Vaciar carrito completo (nuevo)
                linkTo(methodOn(CarritoController.class).vaciarCarritoCompleto(carrito.getUsuarioId())).withRel("vaciar-carrito")
        );
    }
}

