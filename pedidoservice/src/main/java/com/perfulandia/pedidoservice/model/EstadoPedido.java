package com.perfulandia.pedidoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeración que representa los posibles estados de un pedido.
 * Documentada con anotaciones Swagger para la documentación de la API.
 */
@Schema(description = "Estado actual de un pedido")
public enum EstadoPedido {
    @Schema(description = "El pedido ha sido creado pero no ha sido pagado")
    PENDIENTE,

    @Schema(description = "El pedido ha sido pagado")
    PAGADO,

    @Schema(description = "El pedido está siendo preparado para su envío")
    EN_PREPARACION,

    @Schema(description = "El pedido ha sido enviado al cliente")
    ENVIADO,

    @Schema(description = "El pedido ha sido entregado al cliente")
    ENTREGADO,

    @Schema(description = "El pedido ha sido cancelado")
    CANCELADO
}