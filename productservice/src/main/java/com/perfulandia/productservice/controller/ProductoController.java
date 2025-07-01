package com.perfulandia.productservice.controller;

// Importaciones estándar
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Importaciones para Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con productos.
 * Todas las operaciones están disponibles bajo el prefijo "/api/productos".
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Producto Controller", description = "Operaciones CRUD para la gestión de productos") // Define el tag para agrupar operaciones en Swagger UI
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Obtiene la lista completa de productos.
     * @return ResponseEntity con la lista de productos y estado HTTP 200 (OK)
     */
    @Operation(
            summary = "Obtener todos los productos", // Breve descripción de la operación
            description = "Recupera una lista de todos los productos disponibles en el sistema") // Descripción detallada
    @ApiResponse(
            responseCode = "200",
            description = "Lista de productos recuperada exitosamente") // Documenta la respuesta exitosa
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.listar();
        return ResponseEntity.ok(productos);
    }

    /**
     * Crea un nuevo producto.
     * @param producto Datos del producto a crear (en el cuerpo de la petición)
     * @return ResponseEntity con el producto creado y estado HTTP 201 (Created)
     */
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto con los datos proporcionados")
    @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente")
    @ApiResponse(
            responseCode = "400",
            description = "Datos del producto inválidos") // Documenta posibles errores
    @PostMapping
    public ResponseEntity<Producto> guardar(
            @RequestBody
            @Parameter(description = "Objeto Producto que será creado", required = true) // Documenta el parámetro
            Producto producto) {
        Producto productoGuardado = productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    }

    /**
     * Obtiene un producto por su ID.
     * @param id ID del producto a buscar
     * @return ResponseEntity con el producto encontrado (200) o no encontrado (404)
     */
    @Operation(
            summary = "Obtener producto por ID",
            description = "Recupera un producto específico basado en su ID único")
    @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado")
    @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(
            @PathVariable
            @Parameter(description = "ID del producto a buscar", example = "1", required = true) // Ejemplo de valor
            long id) {
        Producto producto = productoService.bucarPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar
     * @return ResponseEntity sin contenido (204) o no encontrado (404)
     */
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto específico basado en su ID único")
    @ApiResponse(
            responseCode = "204",
            description = "Producto eliminado exitosamente")
    @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable
            @Parameter(description = "ID del producto a eliminar", example = "1", required = true)
            long id) {
        if (productoService.bucarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene información de un usuario desde el servicio externo de usuarios.
     * @param idUsuario ID del usuario a buscar
     * @return ResponseEntity con el usuario encontrado (200), no encontrado (404) o error (500)
     */
    @Operation(
            summary = "Obtener información de usuario",
            description = "Recupera información de un usuario desde el servicio externo de usuarios")
    @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado")
    @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado")
    @ApiResponse(
            responseCode = "500",
            description = "Error al comunicarse con el servicio de usuarios")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Usuario> obtenerUsuarioDesdeProductos(
            @PathVariable
            @Parameter(description = "ID del usuario a buscar", example = "123", required = true)
            long idUsuario) {
        try {
            Usuario usuario = productoService.obtenerUsuarioExterno(idUsuario);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}