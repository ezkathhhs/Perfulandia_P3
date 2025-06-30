package com.perfulandia.usuarioservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //Crear objetos de manera flexible = Constructor Flex
public class Usuario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre, correo, rol; // rol: ADMIN, GERENTE, Usuario
}
