package com.titaniumarmor.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String direccion;
}
