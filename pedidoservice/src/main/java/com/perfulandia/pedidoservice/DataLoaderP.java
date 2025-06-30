package com.perfulandia.pedidoservice;

import com.perfulandia.pedidoservice.model.EstadoPedido;
import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.model.PedidoItem;
import com.perfulandia.pedidoservice.repository.PedidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataLoaderP implements CommandLineRunner {

    private final PedidoRepository pedidoRepository;

    public DataLoaderP(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (pedidoRepository.count() == 0) {
            // Crear pedido 1
            Pedido pedido1 = new Pedido();
            pedido1.setUsuarioId(1001L);
            pedido1.setDireccionEnvio("Calle Principal 123");
            pedido1.setCiudadEnvio("Springfield");
            pedido1.setCodigoPostalEnvio("28001");

            // Crear items para pedido 1
            PedidoItem item1 = new PedidoItem();
            item1.setProductoId(1L);
            item1.setNombreProducto("Mando Xbox");
            item1.setCantidad(2);
            item1.setPrecioUnitario(new BigDecimal("35000.00"));

            PedidoItem item2 = new PedidoItem();
            item2.setProductoId(2L);
            item2.setNombreProducto("Monitor LG 24\"");
            item2.setCantidad(1);
            item2.setPrecioUnitario(new BigDecimal("120000.00"));

            // Relacionar items con pedido usando addItem()
            pedido1.addItem(item1);
            pedido1.addItem(item2);

            // Calcular total automáticamente
            pedido1.calcularTotalPedido();

            // Guardar pedido 1 (con cascade se guardan los items)
            pedidoRepository.save(pedido1);

            // Crear pedido 2 (ejemplo adicional)
            Pedido pedido2 = new Pedido();
            pedido2.setUsuarioId(1002L);
            pedido2.setDireccionEnvio("Avenida Siempreviva 742");
            pedido2.setCiudadEnvio("Shelbyville");
            pedido2.setCodigoPostalEnvio("28002");

            PedidoItem item3 = new PedidoItem();
            item3.setProductoId(3L);
            item3.setNombreProducto("Teclado Mecánico");
            item3.setCantidad(3);
            item3.setPrecioUnitario(new BigDecimal("25000.00"));

            pedido2.addItem(item3);
            pedido2.calcularTotalPedido();
            pedidoRepository.save(pedido2);
        }
    }
}