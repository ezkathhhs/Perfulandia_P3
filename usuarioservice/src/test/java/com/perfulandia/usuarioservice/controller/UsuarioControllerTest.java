package com.perfulandia.usuarioservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Constanza");
        usuario.setCorreo("ccardenas@test.com");
        usuario.setRol("ADMIN");
    }

    @Test
    @DisplayName("Debería listar todos los usuarios retornando un JSON y estado 200 OK")
    void listarUsuariosTest() throws Exception {
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuario);

        //When
        // Simulamos que el servicio, al ser llamado, devolverá nuestra lista de usuarios.
        when(usuarioService.listar()).thenReturn(listaUsuarios);

        // Realizamos una petición GET a "/api/usuarios"
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                // Esperamos que el contenido sea de tipo JSON
                .andExpect(content().contentType("application/json"))
                // Usamos JsonPath para verificar el contenido de la respuesta.
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Constanza")));
    }

    @Test
    @DisplayName("Debería eliminar un usuario y retornar estado 200 OK")
    void eliminarUsuarioTest() throws Exception {
        // Le decimos al mock que no haga nada (y no lance error) cuando se llame a eliminar().
        doNothing().when(usuarioService).eliminar(1L);

        // Realizamos una petición DELETE a "/api/usuarios/1"
        mockMvc.perform(delete("/api/usuarios/{id}", 1L))
                .andExpect(status().isOk());

        //Verify
        // Verificamos que el metodo 'eliminar' del servicio fue llamado exactamente 1 vez con el ID 1.
        verify(usuarioService, times(1)).eliminar(1L);
    }
}
