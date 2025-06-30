package com.perfulandia.productservice.Assembler;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.controller.ProductoController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                //GET BY ID
                linkTo(methodOn(ProductoController.class).buscarProducto(producto.getId())).withSelfRel(),
                //GET ALL
                linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos"),
                //POST
                linkTo(methodOn(ProductoController.class).crearProducto(null)).withRel("post"),
                //DELETE
                linkTo(methodOn(ProductoController.class).eliminarProducto(producto.getId())).withRel("delete")
        );
    }
}
