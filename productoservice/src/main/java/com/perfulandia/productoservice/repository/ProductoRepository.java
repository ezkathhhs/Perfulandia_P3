package com.perfulandia.productoservice.repository;

import com.perfulandia.productoservice.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
