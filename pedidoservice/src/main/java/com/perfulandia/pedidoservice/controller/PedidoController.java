package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.model.EstadoPedido;
import com.perfulandia.pedidoservice.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar operaciones relacionadas con pedidos.
 * Documentado con anotaciones Swagger para generar la documentación OpenAPI.
 */
@Tag(name = "Pedidos", description = "API para gestión de pedidos")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // DTOs para las solicitudes
    @Schema(description = "Información de un item para crear un pedido")
    public record CreatePedidoItemRequest(
            @Parameter(description = "ID del producto", example = "1", required = true)
            Long productoId,
            @Parameter(description = "Cantidad del producto", example = "2", required = true)
            int cantidad) {}

    @Schema(description = "Solicitud para crear un nuevo pedido")
    public record CreatePedidoRequest(
            @Parameter(description = "ID del usuario que realiza el pedido", example = "123", required = true)
            Long usuarioId,
            @Parameter(description = "Dirección de envío", example = "Calle Falsa 123", required = true)
            String direccionEnvio,
            @Parameter(description = "Ciudad de envío", example = "Madrid")
            String ciudadEnvio,
            @Parameter(description = "Código postal de envío", example = "28001")
            String codigoPostalEnvio,
            @Parameter(description = "Lista de items del pedido", required = true)
            List<CreatePedidoItemRequest> items) {}

    @Schema(description = "Solicitud para actualizar el estado de un pedido")
    public record UpdatePedidoStatusRequest(
            @Parameter(description = "Nuevo estado del pedido", required = true)
            EstadoPedido nuevoEstado) {}

    /**
     * Crea un nuevo pedido en el sistema.
     *
     * @param request Datos para crear el pedido
     * @return ResponseEntity con el pedido creado o mensaje de error
     */
    @Operation(
            summary = "Crear un nuevo pedido",
            description = "Crea un nuevo pedido con los items proporcionados. " +
                    "Verifica la existencia y disponibilidad de los productos " +
                    "a través del servicio de productos.",
            tags = { "Pedidos" })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de solicitud incompletos o inválidos",
                    content = @Content),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor o problema al contactar el servicio de productos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createPedido(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para crear el pedido",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreatePedidoRequest.class)))
            @RequestBody CreatePedidoRequest request) {
        if (request.usuarioId == null || request.items == null || request.items.isEmpty() || request.direccionEnvio == null || request.direccionEnvio.isBlank()) {
            return ResponseEntity.badRequest().body("Datos de solicitud incompletos o inválidos.");
        }
        try {
            List<PedidoService.CreatePedidoItemInfo> itemsInfo = request.items.stream()
                    .map(itemDto -> new PedidoService.CreatePedidoItemInfo(itemDto.productoId, itemDto.cantidad))
                    .toList();
            Pedido pedido = pedidoService.crearPedido(request.usuarioId, request.direccionEnvio, request.ciudadEnvio, request.codigoPostalEnvio, itemsInfo);
            return new ResponseEntity<>(pedido, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Obtiene los detalles de un pedido específico por su ID.
     *
     * @param pedidoId ID del pedido a buscar
     * @return ResponseEntity con el pedido encontrado o 404 si no existe
     */
    @Operation(
            summary = "Obtener pedido por ID",
            description = "Retorna un pedido específico por su ID con todos sus detalles",
            tags = { "Pedidos" })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido encontrado",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content)
    })
    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> getPedidoById(
            @Parameter(description = "ID del pedido a buscar", example = "1", required = true)
            @PathVariable Long pedidoId) {
        return pedidoService.obtenerPedidoPorId(pedidoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los pedidos realizados por un usuario específico.
     *
     * @param usuarioId ID del usuario
     * @return Lista de pedidos del usuario
     */
    @Operation(
            summary = "Obtener pedidos por usuario",
            description = "Retorna todos los pedidos realizados por un usuario específico",
            tags = { "Pedidos" })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos del usuario",
                    content = @Content(schema = @Schema(implementation = Pedido.class)))
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> getPedidosByUsuarioId(
            @Parameter(description = "ID del usuario", example = "123", required = true)
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorUsuario(usuarioId));
    }

    /**
     * Actualiza el estado de un pedido existente.
     *
     * @param pedidoId ID del pedido a actualizar
     * @param request Nuevo estado del pedido
     * @return Pedido actualizado o 404 si no existe
     */
    @Operation(
            summary = "Actualizar estado de pedido",
            description = "Modifica el estado de un pedido existente (PENDIENTE, PAGADO, EN_PREPARACION, etc.)",
            tags = { "Pedidos" })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado del pedido actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Nuevo estado no proporcionado o inválido",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content)
    })
    @PutMapping("/{pedidoId}/estado")
    public ResponseEntity<Pedido> updatePedidoStatus(
            @Parameter(description = "ID del pedido a actualizar", example = "1", required = true)
            @PathVariable Long pedidoId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevo estado del pedido",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdatePedidoStatusRequest.class)))
            @RequestBody UpdatePedidoStatusRequest request) {
        if (request.nuevoEstado == null) return ResponseEntity.badRequest().build();
        return pedidoService.actualizarEstadoPedido(pedidoId, request.nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene una lista de todos los pedidos registrados en el sistema.
     *
     * @return Lista de todos los pedidos
     */
    @Operation(
            summary = "Listar todos los pedidos",
            description = "Retorna todos los pedidos registrados en el sistema",
            tags = { "Pedidos" })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de todos los pedidos",
                    content = @Content(schema = @Schema(implementation = Pedido.class)))
    })
    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    /**
     * Elimina un pedido específico del sistema por su ID.
     *
     * @param pedidoId ID del pedido a eliminar
     * @return 204 si se eliminó correctamente, 404 si no existe
     */
    @Operation(
            summary = "Eliminar pedido",
            description = "Elimina un pedido específico del sistema por su ID",
            tags = { "Pedidos" })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Pedido eliminado exitosamente",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Void> deletePedido(
            @Parameter(description = "ID del pedido a eliminar", example = "1", required = true)
            @PathVariable Long pedidoId) {
        if (pedidoService.eliminarPedidoPorId(pedidoId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}