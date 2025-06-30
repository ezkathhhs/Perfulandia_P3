package com.perfulandia.productservice;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Dataloader implements CommandLineRunner {
    private final ProductoRepository Prepo;

    public Dataloader(ProductoRepository Prepo) {
        this.Prepo = Prepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (Prepo.count() == 0) {
            Producto Mando = new Producto(null, "Mando", 35000, 100);
            Producto Teclado = new Producto(null, "Teclado", 50000, 60);
            Producto Monitor = new Producto(null, "Monitor", 120000, 55);

            Prepo.save(Mando);
            Prepo.save(Teclado);
            Prepo.save(Monitor);
        }
    }
}
