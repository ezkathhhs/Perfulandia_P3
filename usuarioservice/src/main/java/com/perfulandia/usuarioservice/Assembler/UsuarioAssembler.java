package com.perfulandia.usuarioservice.Assembler;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.controller.UsuarioController;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios")
        );
    }

}
