package com.perfulandia.productoservice.controller;

import com.perfulandia.productservice.controller.ProductoController;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    //Test GetAll
    void testGetAll() throws Exception {
        when(productoService.listar()).thenReturn(List.of(new Producto(1L,"Pendrive",1000D,1)));
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].nombre").value("Pendrive"));
    }

    @Test
    @DisplayName("Eliminar")
    void testEliminar() throws Exception {
        doNothing().when(productoService).eliminar(1L);
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk());
    }
}
