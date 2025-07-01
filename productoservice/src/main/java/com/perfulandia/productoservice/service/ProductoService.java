package com.perfulandia.productoservice.service;

import com.perfulandia.productoservice.model.Producto;
import com.perfulandia.productoservice.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // Necesario para la comunicación

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final RestTemplate restTemplate;

    public ProductoService(ProductoRepository productoRepository, RestTemplate restTemplate){
        this.productoRepository = productoRepository;
        this.restTemplate = restTemplate;
    }

    // listar
    public List<Producto> listar(){
        return productoRepository.findAll();
    }

    // Guardar
    public Producto guardar(Producto producto){
        return productoRepository.save(producto);
    }

    // Buscar por id
    public Producto bucarPorId(long id){
        return productoRepository.findById(id).orElse(null);
    }

    // Eliminar por id
    public void eliminar(long id){
        productoRepository.deleteById(id);
    }
}