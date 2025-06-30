package com.perfulandia.productoservice.service;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.repository.ProductoRepository;

import com.perfulandia.productservice.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Mockito
import org.mockito.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;
    public ProductoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("buscar todo")
    void testFindAll( ){
        when(productoRepository.findAll()).thenReturn(List.of(new Producto(1L,"Pendrive",1000D,1)));
        List<Producto> productos = productoRepository.findAll();
        assertEquals(1,productos.size());
    }
}
