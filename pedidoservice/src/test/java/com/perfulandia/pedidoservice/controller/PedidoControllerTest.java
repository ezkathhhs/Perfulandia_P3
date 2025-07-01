package com.perfulandia.pedidoservice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @Test
    void testGetAllPedidos() throws Exception {
        // Configurar mock
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);
        //When
        when(pedidoService.listarTodos()).thenReturn(pedidos);

        // Ejecutar y verificar
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
        System.out.println("Verificación exitosa: Test de endpoint para listar pedidos exitoso. ✅");
    }

    @Test
    void testGetPedidoById() throws Exception {
        // Caso exitoso (pedido existe)
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        //When
        when(pedidoService.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        System.out.println("Verificación exitosa: Test de endpoint para buscar pedido por su id exitoso. ✅");

        //When
        // Caso fallido (pedido no existe)
        when(pedidoService.obtenerPedidoPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/pedidos/2"))
                .andExpect(status().isNotFound());
    }
}
