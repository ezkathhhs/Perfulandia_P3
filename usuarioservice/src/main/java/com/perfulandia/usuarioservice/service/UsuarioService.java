package com.perfulandia.usuarioservice.service;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    //Constructor para poder consumir la interfaz
    public UsuarioService(UsuarioRepository repo){
        this.repo=repo;
    }
    //Listar
    public List<Usuario> listar(){
        return repo.findAll();
    }
    //Guardar
    public Usuario guardar(Usuario usuario){
        return repo.save(usuario);
    }
    //Buscar por id
    public Usuario buscar(Long id){
        return repo.findById(id).orElse(null);
    }
    //Eliminar id
    public void eliminar(Long id){
        repo.deleteById(id);
    }
}
