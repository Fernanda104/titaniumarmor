package com.titaniumarmor.auth_service.service;

import com.titaniumarmor.auth_service.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {

    private final WebClient webClient;
    private final JwtService jwtService;

    @Value("${api.usuario.email}")
    private String usuarioEmailPath;

    public AuthService(WebClient webClient, JwtService jwtService) {
        this.webClient = webClient;
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {

        UsuarioDTO usuario = webClient.get()
                .uri(String.format(usuarioEmailPath, email))
                .retrieve()
                .bodyToMono(UsuarioDTO.class)
                .block();

        if (usuario == null) {
            return null;
        }

        if (!password.equals(usuario.getPassword())) {
            return null;
        }

        return jwtService.generateToken(usuario.getEmail());
    }
}
