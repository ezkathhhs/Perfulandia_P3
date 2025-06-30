package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.Assembler.CarritoAssembler;
import com.perfulandia.carritoservice.Assembler.CarritoItemAssembler;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.service.CarritoService;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Optional;

@RestController // Maneja solicitudes HTTP
@RequestMapping("/api/carritos") // Establece que los endpoints comenzarán como (/api/carritos)
public class CarritoController {
    private final CarritoService carritoService;
    private final CarritoAssembler carritoAssembler;
    private final CarritoItemAssembler itemAssembler;

    public CarritoController(CarritoService carritoService, CarritoAssembler carritoAssembler, CarritoItemAssembler itemAssembler) {
        this.itemAssembler = itemAssembler;
        this.carritoService = carritoService;
        this.carritoAssembler = carritoAssembler;
    }

    public record AddItemToCartRequest(
            Long productoid,
            String nombreProducto,
            Integer cantidad,
            BigDecimal precioUnitario
    ) {}
    public record UpdateItemQuantityRequest(Integer nuevaCantidad) {}

    // Agregar item al carrito
    @PostMapping("/usuario/{usuarioId}/items")
    public ResponseEntity<EntityModel<Carrito>> addItemToCart(
            @PathVariable Long usuarioId,
            @RequestBody AddItemToCartRequest request) {

        if (request.productoid() == null || request.cantidad() <= 0 ||
                request.precioUnitario() == null || request.nombreProducto() == null ||
                request.nombreProducto().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Carrito carrito = carritoService.agregarItem(
                usuarioId,
                request.productoid(),
                request.nombreProducto(),
                request.cantidad(),
                request.precioUnitario()
        );

        return ResponseEntity.ok(carritoAssembler.toModel(carrito));
    }

    // Obtener un carrito específico por su propio ID.
    @GetMapping("/{carritoId}")
    public ResponseEntity<Carrito> getCartById(@PathVariable Long carritoId) {
        return carritoService.obtenerCarritoPorId(carritoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener carrito por ID de usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<EntityModel<Carrito>> getCarritoByUsuarioId(@PathVariable Long usuarioId) {
        Carrito carrito = carritoService.obtenerOCrearCarrito(usuarioId);
        return ResponseEntity.ok(carritoAssembler.toModel(carrito));
    }

    // Elimina todos los items que tenga un carrito
    @DeleteMapping("/usuario/{usuarioId}/items")
    public ResponseEntity<EntityModel<Carrito>> vaciarCarritoCompleto(
            @PathVariable Long usuarioId) {

        return carritoService.vaciarCarrito(usuarioId)
                .map(carritoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}