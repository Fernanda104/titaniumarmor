package com.titaniumarmor.usuarios_service;

import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.titaniumarmor.usuarios_service.model.Usuario;
import com.titaniumarmor.usuarios_service.repository.UsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private UsuarioRepository usuarioRepository;

    
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar usuarios
        for (long i = 0l; i < 3; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setPassword(faker.internet().password());
            usuario.setDireccion(faker.address().fullAddress());
            usuarioRepository.save(usuario);
        }



    }
}
