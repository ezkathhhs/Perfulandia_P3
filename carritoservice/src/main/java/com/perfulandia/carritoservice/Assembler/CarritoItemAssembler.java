package com.perfulandia.carritoservice.Assembler;

import com.perfulandia.carritoservice.controller.CarritoController;
import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.productservice.controller.ProductoController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoItemAssembler implements RepresentationModelAssembler<CarritoItem, EntityModel<CarritoItem>> {

    @Override
    public EntityModel<CarritoItem> toModel(CarritoItem item) {
        return EntityModel.of(item,
                // Carrito padre
                linkTo(methodOn(CarritoController.class).getCartById(item.getCarrito().getId())).withRel("carrito"),

                // Producto relacionado (en productoservice)
                linkTo(methodOn(ProductoController.class).buscarProducto(item.getProductoId())).withRel("producto"),

                // Vaciar todo el carrito (nuevo)
                linkTo(methodOn(CarritoController.class).vaciarCarritoCompleto(item.getCarrito().getUsuarioId())).withRel("vaciar-carrito")
        );
    }
}
