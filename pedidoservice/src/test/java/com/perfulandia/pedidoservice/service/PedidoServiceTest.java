package com.perfulandia.pedidoservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.repository.PedidoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    //Mock
    @Mock
    private PedidoRepository pedidoRepository;

    //InjectMocks
    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void testListarTodos() {
        // Configurar mock
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);

        //When
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        // Ejecutar metodo
        List<Pedido> resultado = pedidoService.listarTodos();

        // Verificar
        assertEquals(2, resultado.size());

        //Verify
        verify(pedidoRepository, times(1)).findAll();
        System.out.println("Verificación exitosa: Se listaron todos los pedidos. ✅");
    }

    @Test
    void testEliminarPedidoPorId() {
        //When
        // Caso exitoso (pedido existe)
        when(pedidoRepository.existsById(1L)).thenReturn(true);
        assertTrue(pedidoService.eliminarPedidoPorId(1L));

        //Verify
        verify(pedidoRepository, times(1)).deleteById(1L);

        //When
        // Caso fallido (pedido no existe)
        when(pedidoRepository.existsById(2L)).thenReturn(false);
        assertFalse(pedidoService.eliminarPedidoPorId(2L));

        //Verify
        verify(pedidoRepository, never()).deleteById(2L);
        System.out.println("Verificación exitosa: El pedido fue eliminado por su id. ✅");
    }
}
