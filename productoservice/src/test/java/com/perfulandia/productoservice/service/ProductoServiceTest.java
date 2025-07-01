package com.perfulandia.productoservice.service;

import com.perfulandia.productoservice.model.Producto;
import com.perfulandia.productoservice.repository.ProductoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository; // El objeto que simulamos (mock)

    @InjectMocks
    private ProductoService productoService; // La clase que estamos probando, con los mocks inyectados

    private Producto producto;

    @BeforeEach
    void setUp() {
        // Creamos un objeto de prueba antes de cada test
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Invictus");
        producto.setPrecio(85000);
        producto.setStock(50);
    }

    @Test
    void testListarTodosLosProductos() {
        // Cuando se llame a findAll, devuelve una lista con nuestro producto de prueba
        when(productoRepository.findAll()).thenReturn(Collections.singletonList(producto));

        List<Producto> productos = productoService.listar();

        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findAll(); // Verifica que el metodo findAll fue llamado una vez
        System.out.println("Test para listar productos exitoso ✅");
    }

    @Test
    void testEliminarProducto() {
        // No necesitamos que doNothing() devuelva algo, solo queremos verificar que se llama
        doNothing().when(productoRepository).deleteById(1L);
        productoService.eliminar(1L);

        // Verificamos que el metodo deleteById del repositorio fue llamado exactamente 1 vez con el ID 1.
        verify(productoRepository, times(1)).deleteById(1L);
        System.out.println("Test para eliminar producto exitoso ✅");
    }
}