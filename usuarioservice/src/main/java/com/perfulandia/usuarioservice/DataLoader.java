package com.perfulandia.usuarioservice;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final UsuarioRepository Urepo;

    public DataLoader(UsuarioRepository Urepo) {
        this.Urepo = Urepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (Urepo.count() == 0) {
            Usuario miguel = new Usuario(null, "Miguel", "miguel@gmail.com", "Usuario");
            Usuario angel = new Usuario(null, "Angel", "angel@gmail.com", "Gerente");
            Usuario coni = new Usuario(null, "Constanza", "constanza@gmail.com", "Admin");

            Urepo.save(miguel);
            Urepo.save(angel);
            Urepo.save(coni);
        }
    }
}
