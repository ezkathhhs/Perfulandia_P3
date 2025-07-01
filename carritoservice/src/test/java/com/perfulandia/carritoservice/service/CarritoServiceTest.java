package com.perfulandia.carritoservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    @Test
    @Transactional
    void testAgregarItem() {
        // Configurar datos de prueba
        Long usuarioId = 1L;
        Long productoId = 100L;
        String nombreProducto = "Producto Test";
        int cantidad = 2;
        BigDecimal precioUnitario = BigDecimal.valueOf(50000);
        // Configurar comportamiento del repositorio
        when(carritoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Ejecutar metodo
        Carrito resultado = carritoService.agregarItem(usuarioId, productoId, nombreProducto, cantidad, precioUnitario);
        // Verificar resultados
        assertNotNull(resultado);
        assertEquals(1, resultado.getItems().size());
        CarritoItem item = resultado.getItems().get(0);
        assertEquals(productoId, item.getProductoId());
        assertEquals(nombreProducto, item.getNombreProducto());
        assertEquals(cantidad, item.getCantidad());
        assertEquals(precioUnitario, item.getPrecioUnitario());
        System.out.println("Verificación exitosa: Se agrego un producto al carrito exitosamente. ✅");
    }
    @Test
    @Transactional
    void testVaciarCarrito() {
        // Configurar datos de prueba
        Long usuarioId = 1L;
        Carrito carrito = new Carrito();
        carrito.setUsuarioId(usuarioId);
        carrito.addItem(new CarritoItem(100L,2L, "Producto 1", 2, BigDecimal.valueOf(50000), carrito));
        // Configurar comportamiento del repositorio
        when(carritoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Ejecutar metodo
        Optional<Carrito> resultado = carritoService.vaciarCarrito(usuarioId);
        // Verificar resultados
        assertTrue(resultado.isPresent());
        assertEquals(0, resultado.get().getItems().size());
        verify(carritoRepository, times(1)).save(carrito);
        System.out.println("Verificación exitosa: No se encontraron productos en el carrito exitosamente. ✅");
    }
}