package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.Assembler.PedidoAssembler;
import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.model.EstadoPedido;
import com.perfulandia.pedidoservice.service.PedidoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // Maneja solicitudes HTTP
@RequestMapping("/api/pedidos") // Establece que los endpoints comenzarán como (/api/pedidos)
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoAssembler pedidoAssembler;

    public PedidoController(PedidoService pedidoService, PedidoAssembler pedidoAssembler) {
        this.pedidoService = pedidoService;
        this.pedidoAssembler = pedidoAssembler;
    }

    public record CreatePedidoItemRequest(Long productoId, int cantidad) {}
    public record CreatePedidoRequest(Long usuarioId, String direccionEnvio, String ciudadEnvio, String codigoPostalEnvio, List<CreatePedidoItemRequest> items) {}
    public record UpdatePedidoStatusRequest(EstadoPedido nuevoEstado) {}

    // Crear un nuevo pedido en el sistema.
    @PostMapping
    public ResponseEntity<EntityModel<Pedido>> crearPedido(@RequestBody CreatePedidoRequest request) {
        try {
            // Convertir a objetos del dominio
            List<PedidoService.CreatePedidoItemInfo> itemInfo = request.items().stream()
                    .map(item -> new PedidoService.CreatePedidoItemInfo(item.productoId(), item.cantidad()))
                    .collect(Collectors.toList());

            Pedido pedido = pedidoService.crearPedido(
                    request.usuarioId(),
                    request.direccionEnvio(),
                    request.ciudadEnvio(),
                    request.codigoPostalEnvio(),
                    itemInfo
            );

            EntityModel<Pedido> resource = pedidoAssembler.toModel(pedido);

            return ResponseEntity
                    .created(resource.getRequiredLink("self").toUri())
                    .body(resource);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Obtener pedido por ID
    @GetMapping("/{pedidoId}")
    public ResponseEntity<EntityModel<Pedido>> buscarPedido(@PathVariable Long pedidoId) {
        return pedidoService.obtenerPedidoPorId(pedidoId)
                .map(pedidoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener pedidos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public CollectionModel<EntityModel<Pedido>> buscarPedidosByUsuarioId(@PathVariable Long usuarioId) {
        List<EntityModel<Pedido>> pedidos = pedidoService.obtenerPedidosPorUsuario(usuarioId).stream()
                .map(pedidoAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).buscarPedidosByUsuarioId(usuarioId)).withSelfRel());
    }

    // Obtener una lista de todos los pedidos registrados.
    @GetMapping
    public CollectionModel<EntityModel<Pedido>> listarPedidos() {
        List<EntityModel<Pedido>> pedidos = pedidoService.listarTodos().stream()
                .map(pedidoAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listarPedidos()).withSelfRel(),
                linkTo(methodOn(PedidoController.class).crearPedido(null)).withRel("create"));
    }

    // Eliminar un pedido
    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long pedidoId) {
        if (pedidoService.eliminarPedidoPorId(pedidoId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}