package com.perfulandia.usuarioservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.perfulandia.usuarioservice.Assembler.UsuarioAssembler;
import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.service.UsuarioService;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;

import java.util.List;
import org.springframework.hateoas.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService service;

    //Constructor para poder consumir la interfaz
    public UsuarioController(UsuarioService service, UsuarioAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    //Antiguos metodos
    //GET ALL
    //@GetMapping
    //public List<Usuario> listar(){
    //return service.listar();
    //}

    //POST
    //@PostMapping
    //public Usuario guardar(@RequestBody Usuario usuario){
    //return service.guardar(usuario);
    //}

    //GET BY ID
    //@GetMapping("/{id}")
    //public Usuario buscar(@PathVariable long id){
    //return service.buscar(id);
    //}

    //DELETE
    //@DeleteMapping("/{id}")
    //public void eliminar(@PathVariable long id){
    //service.eliminar(id);
    //}


    //HateAOS
    private final UsuarioAssembler assembler;

    //GET ALL
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> listarUsuarios() {
        //Obteniendo todos los usuarios, y transformandolos en EntityModel con enlaces de Assembler
        List<EntityModel<Usuario>> usuarios = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());


        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withSelfRel());
    }

    //GET BY ID
    @GetMapping("/{id}")
    public EntityModel<Usuario> buscarUsuario(@PathVariable Long id) {

        Usuario usuario = service.buscar(id);
        return assembler.toModel(usuario);
    }

    //POST
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = service.guardar(usuario);

        return ResponseEntity.created(linkTo(methodOn(UsuarioController.class).buscarUsuario(nuevoUsuario.getId())).toUri()).body(assembler.toModel(nuevoUsuario));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        service.eliminar(id);

        return ResponseEntity.noContent().header("Location", linkTo(methodOn(UsuarioController.class).listarUsuarios()).toUri().toString()).build();
    }
}
