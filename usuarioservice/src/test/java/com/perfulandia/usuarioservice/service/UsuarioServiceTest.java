package com.perfulandia.usuarioservice.service;

import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import com.perfulandia.usuarioservice.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    //Mock
    @Mock
    private UsuarioRepository usuarioRepository;

    //InjectMocks
    @InjectMocks
    private UsuarioService usuarioService;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Constanza");
        usuario.setCorreo("ccardenas@test.com");
        usuario.setRol("ADMIN");
    }

    @Test
    @DisplayName("Debería guardar un usuario y verificar la interacción")
    void guardarUsuarioTest() {
        //When
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario resultado = usuarioService.guardar(new Usuario());

        assertNotNull(resultado);
        assertEquals("Constanza", resultado.getNombre());
        System.out.println("Test 'guardarUsuarioTest' pasado: El usuario fue guardado y retornado correctamente.");

        //Verify
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        System.out.println("Verificación exitosa: El método save() del repositorio fue llamado una vez.");
    }

    @Test
    @DisplayName("Debería retornar nulo si el usuario a buscar no existe")
    void buscarUsuarioInexistenteTest() {
        //When
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Llamamos al metodo 'buscar' con un ID de ejemplo (ej. 99).
        Usuario resultado = usuarioService.buscar(99L);

        assertNull(resultado);
        System.out.println("Test 'buscarUsuarioInexistenteTest' pasado: Retornó null como se esperaba.");

        //Verify
        // Verificamos que el metodo findById fue llamado en el repositorio.
        verify(usuarioRepository, times(1)).findById(99L);
    }
}