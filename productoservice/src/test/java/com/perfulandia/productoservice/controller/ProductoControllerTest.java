package com.perfulandia.productoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.perfulandia.productoservice.model.Producto;
import com.perfulandia.productoservice.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ProductoController.class) // Indicamos que solo probaremos la capa web para ProductoController
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Objeto para simular peticiones HTTP

    @MockitoBean
    private ProductoService productoService; // Mockeamos el servicio para aislar el controller
    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Invictus");
        producto.setPrecio(85000);
        producto.setStock(50);
    }

    // 3. Test para el endpoint que lista todos los productos (GET)
    @Test
    void testListarTodosLosProductosEndpoint() throws Exception {
        when(productoService.listar()).thenReturn(Collections.singletonList(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Verifica que la lista en el JSON tiene 1 elemento
                .andExpect(jsonPath("$[0].nombre", is("Invictus")));
        System.out.println("Test de endpoint para listar productos exitoso ✅");
    }

    // 4. Test para el endpoint que elimina un producto (DELETE)
    @Test
    void testEliminarProductoEndpoint() throws Exception {
        when(productoService.bucarPorId(1L)).thenReturn(producto);
        doNothing().when(productoService).eliminar(1L);

        mockMvc.perform(delete("/api/productos/1")) // Simula una petición DELETE a /productos/1
                .andExpect(status().isNoContent()); // Espera una respuesta HTTP 204 No Content

        verify(productoService, times(1)).bucarPorId(1L);
        verify(productoService, times(1)).eliminar(1L);

        System.out.println("Test de endpoint para eliminar producto exitoso ✅");
    }
}