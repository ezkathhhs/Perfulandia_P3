package com.perfulandia.carritoservice;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoaderC implements CommandLineRunner {

    private final CarritoRepository carritoRepository;

    public DataLoaderC(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo crear datos si no existen carritos
        if (carritoRepository.count() == 0) {
            // Asumimos usuarios existentes (IDs de usuarioservice)
            long usuario1 = 1001L;
            long usuario2 = 1002L;

            // Asumimos productos existentes (IDs de productoservice)
            long producto1 = 1L;  // Mando Xbox
            long producto2 = 2L;  // Monitor LG 24"
            long producto3 = 3L;  // Teclado Mecánico

            // Carrito 1 - Usuario 1001
            Carrito carrito1 = new Carrito();
            carrito1.setUsuarioId(usuario1);
            carrito1.setFechaCreacion(LocalDateTime.now().minusDays(2));
            carrito1.setFechaActualizacion(LocalDateTime.now().minusHours(3));

            // Items para carrito1
            CarritoItem item1 = new CarritoItem();
            item1.setProductoId(producto1);
            item1.setNombreProducto("Mando Xbox");
            item1.setCantidad(2);
            item1.setPrecioUnitario(new BigDecimal("35000.00"));

            CarritoItem item2 = new CarritoItem();
            item2.setProductoId(producto2);
            item2.setNombreProducto("Monitor LG 24\"");
            item2.setCantidad(1);
            item2.setPrecioUnitario(new BigDecimal("120000.00"));

            // Agregar items usando relación bidireccional
            carrito1.addItem(item1);
            carrito1.addItem(item2);

            // Carrito 2 - Usuario 1002
            Carrito carrito2 = new Carrito();
            carrito2.setUsuarioId(usuario2);
            carrito2.setFechaCreacion(LocalDateTime.now().minusDays(1));
            carrito2.setFechaActualizacion(LocalDateTime.now().minusHours(1));

            // Items para carrito2
            CarritoItem item3 = new CarritoItem();
            item3.setProductoId(producto3);
            item3.setNombreProducto("Teclado Mecánico");
            item3.setCantidad(3);
            item3.setPrecioUnitario(new BigDecimal("25000.00"));

            CarritoItem item4 = new CarritoItem();
            item4.setProductoId(producto1);  // Mismo producto que en carrito1
            item4.setNombreProducto("Mando Xbox");
            item4.setCantidad(1);
            item4.setPrecioUnitario(new BigDecimal("35000.00"));

            carrito2.addItem(item3);
            carrito2.addItem(item4);

            // Guardar ambos carritos (los items se guardan en cascada)
            carritoRepository.saveAll(Arrays.asList(carrito1, carrito2));

            System.out.println("Datos iniciales de carritos creados:");
            System.out.println(" - Carrito 1 para usuario " + usuario1 + " con " + carrito1.getItems().size() + " items");
            System.out.println(" - Carrito 2 para usuario " + usuario2 + " con " + carrito2.getItems().size() + " items");
        }
    }
}