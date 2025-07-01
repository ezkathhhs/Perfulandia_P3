package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/carritos")
@Tag(name = "Carrito Controller", description = "Operaciones para gestionar carritos de compra")
public class CarritoController {
    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // Clases internas para request bodies
    public static class AddItemToCartRequest {
        @Parameter(description = "ID del producto a agregar", example = "1", required = true)
        public Long productoId;

        @Parameter(description = "Nombre del producto", example = "Laptop HP", required = true)
        public String nombreProducto;

        @Parameter(description = "Cantidad a agregar", example = "2", required = true)
        public int cantidad;

        @Parameter(description = "Precio unitario del producto", example = "999.99", required = true)
        public BigDecimal precioUnitario;
    }

    public static class UpdateItemQuantityRequest {
        @Parameter(description = "Nueva cantidad del ítem", example = "3", required = true)
        public int nuevaCantidad;
    }

    @Operation(summary = "Obtener carrito por ID de usuario", description = "Recupera o crea un carrito para el usuario")
    @ApiResponse(responseCode = "200", description = "Carrito encontrado/creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carrito.class)))
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Carrito> getCarritoByUsuarioId(
            @Parameter(description = "ID del usuario", example = "101") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerOCrearCarrito(usuarioId));
    }

    @Operation(summary = "Agregar ítem al carrito", description = "Agrega o actualiza un producto en el carrito")
    @ApiResponse(responseCode = "200", description = "Ítem agregado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carrito.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping("/usuario/{usuarioId}/items")
    public ResponseEntity<Carrito> addItemToCart(
            @Parameter(description = "ID del usuario", example = "101") @PathVariable Long usuarioId,
            @RequestBody AddItemToCartRequest request) {
        if (request.productoId == null || request.cantidad <= 0 || request.precioUnitario == null ||
                request.nombreProducto == null || request.nombreProducto.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Carrito carrito = carritoService.agregarItem(
                usuarioId, request.productoId, request.nombreProducto,
                request.cantidad, request.precioUnitario);
        return ResponseEntity.ok(carrito);
    }

    @Operation(summary = "Actualizar cantidad", description = "Modifica la cantidad de un ítem o lo elimina si la cantidad es 0")
    @ApiResponse(responseCode = "200", description = "Cantidad actualizada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carrito.class)))
    @ApiResponse(responseCode = "400", description = "Cantidad inválida")
    @ApiResponse(responseCode = "404", description = "Carrito/ítem no encontrado")
    @PutMapping("/usuario/{usuarioId}/items/{productoId}")
    public ResponseEntity<Carrito> updateItemQuantity(
            @Parameter(description = "ID del usuario", example = "101") @PathVariable Long usuarioId,
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long productoId,
            @RequestBody UpdateItemQuantityRequest request) {
        if (request.nuevaCantidad < 0) return ResponseEntity.badRequest().build();
        return carritoService.actualizarCantidadItem(usuarioId, productoId, request.nuevaCantidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar ítem", description = "Remueve un producto del carrito")
    @ApiResponse(responseCode = "200", description = "Ítem eliminado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carrito.class)))
    @ApiResponse(responseCode = "404", description = "Carrito/ítem no encontrado")
    @DeleteMapping("/usuario/{usuarioId}/items/{productoId}")
    public ResponseEntity<Carrito> removeItemFromCart(
            @Parameter(description = "ID del usuario", example = "101") @PathVariable Long usuarioId,
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long productoId) {
        return carritoService.removerItem(usuarioId, productoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Vaciar carrito", description = "Elimina todos los ítems del carrito")
    @ApiResponse(responseCode = "200", description = "Carrito vaciado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carrito.class)))
    @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Carrito> clearCart(
            @Parameter(description = "ID del usuario", example = "101") @PathVariable Long usuarioId) {
        return carritoService.vaciarCarrito(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener carrito por ID", description = "Recupera un carrito por su ID único")
    @ApiResponse(responseCode = "200", description = "Carrito encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carrito.class)))
    @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    @GetMapping("/{carritoId}")
    public ResponseEntity<Carrito> getCartById(
            @Parameter(description = "ID del carrito", example = "1") @PathVariable Long carritoId) {
        return carritoService.obtenerCarritoPorId(carritoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar carrito", description = "Borra completamente un carrito")
    @ApiResponse(responseCode = "204", description = "Carrito eliminado")
    @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    @DeleteMapping("/{carritoId}")
    public ResponseEntity<Void> deleteCartById(
            @Parameter(description = "ID del carrito", example = "1") @PathVariable Long carritoId) {
        if (carritoService.obtenerCarritoPorId(carritoId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        carritoService.eliminarCarritoPorId(carritoId);
        return ResponseEntity.noContent().build();
    }
}