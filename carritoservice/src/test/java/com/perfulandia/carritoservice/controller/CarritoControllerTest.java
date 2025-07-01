package com.perfulandia.carritoservice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CarritoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoController carritoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carritoController).build();
    }

    @Test
    void testGetCartById() throws Exception {
        // Configurar carrito existente
        Long carritoId = 1L;
        Carrito carrito = new Carrito();
        carrito.setId(carritoId);
        when(carritoService.obtenerCarritoPorId(carritoId)).thenReturn(Optional.of(carrito));

        // Ejecutar y verificar
        mockMvc.perform(get("/api/carritos/{carritoId}", carritoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carritoId));
        System.out.println("Verificación exitosa: Test de endpoint para listar carrito por id exitoso. ✅");

        // Caso no encontrado
        when(carritoService.obtenerCarritoPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/carritos/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCartById() throws Exception {
        // Caso exitoso
        Long carritoId = 1L;
        when(carritoService.obtenerCarritoPorId(carritoId)).thenReturn(Optional.of(new Carrito()));

        mockMvc.perform(delete("/api/carritos/{carritoId}", carritoId))
                .andExpect(status().isNoContent());

        // Verificar interacción con el servicio
        verify(carritoService, times(1)).eliminarCarritoPorId(carritoId);
        System.out.println("Verificación exitosa: Test de endpoint para eliminar carrito por id exitoso. ✅");

        // Caso no encontrado
        when(carritoService.obtenerCarritoPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/carritos/2"))
                .andExpect(status().isNotFound());
    }
}
