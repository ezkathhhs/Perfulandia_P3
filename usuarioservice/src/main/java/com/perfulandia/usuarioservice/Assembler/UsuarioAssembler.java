package com.perfulandia.usuarioservice.Assembler;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.controller.UsuarioController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                //GET BY ID
                linkTo(methodOn(UsuarioController.class).buscarUsuario(usuario.getId())).withSelfRel(),
                //GET ALL
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios"),
                //POST
                linkTo(methodOn(UsuarioController.class).crearUsuario(null)).withRel("post"),
                //DELETE
                linkTo(methodOn(UsuarioController.class).eliminarUsuario(usuario.getId())).withRel("delete")
        );
    }
}