package com.perfulandia.productservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.perfulandia.productservice.Assembler.ProductoAssembler;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.service.ProductoService;
import com.perfulandia.productservice.repository.ProductoRepository;

import java.util.List;
import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
public class ProductoController {

    private final ProductoService service;

    //Constructor para poder consumir la interfaz
    public ProductoController(ProductoService service, ProductoAssembler assembler){
        this.service = service;
        this.assembler = assembler;
    }

    //@GetMapping
    //public ResponseEntity<List<Producto>> listar(){
        //List<Producto> productos = productoService.listar();
        //return ResponseEntity.ok(productos);
    //}

    //@PostMapping
    //public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        //Producto productoGuardado = productoService.guardar(producto);
        //return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    //}

    //@GetMapping("/{id}")
    //public ResponseEntity<Producto> buscar(@PathVariable long id){
        //Producto producto = productoService.bucarPorId(id);
        //if (producto != null) {
            //return ResponseEntity.ok(producto);
        //} else {
            //return ResponseEntity.notFound().build();
        //}
    //}

    //@DeleteMapping("/{id}")
    //public ResponseEntity<Void> eliminar(@PathVariable long id){
        //if (productoService.bucarPorId(id) == null) {
            //return ResponseEntity.notFound().build();
        //}
        //productoService.eliminar(id);
        //return ResponseEntity.noContent().build();
    //}

    //@GetMapping("/usuario/{idUsuario}")
    //public ResponseEntity<Usuario> obtenerUsuarioDesdeProductos(@PathVariable long idUsuario){
        //try {
            //Usuario usuario = productoService.obtenerUsuarioExterno(idUsuario);
            //if (usuario != null) {
                //return ResponseEntity.ok(usuario);
            //} else {
                //return ResponseEntity.notFound().build();
            //}
        //} catch (RuntimeException e) {
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        //}
    //}


    //HateAOS
    private final ProductoAssembler assembler;

    //GET ALL
    @GetMapping
    public CollectionModel<EntityModel<Producto>> listarProductos() {
        //Obteniendo todos los productos, y transformandolos en EntityModel con enlaces de Assembler
        List<EntityModel<Producto>> productos = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());


        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listarProductos()).withSelfRel());
    }

    //GET BY ID
    @GetMapping("/{id}")
    public EntityModel<Producto> buscarProducto(@PathVariable Long id) {

        Producto producto = service.buscar(id);
        return assembler.toModel(producto);
    }

    //POST
    @PostMapping
    public ResponseEntity<EntityModel<Producto>> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = service.guardar(producto);

        return ResponseEntity.created(linkTo(methodOn(ProductoController.class).buscarProducto(nuevoProducto.getId())).toUri()).body(assembler.toModel(nuevoProducto));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        service.eliminar(id);

        return ResponseEntity.noContent().header("Location", linkTo(methodOn(ProductoController.class).listarProductos()).toUri().toString()).build();
    }
}