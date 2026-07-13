package com.titaniumarmor.soporte_service;

import com.titaniumarmor.soporte_service.model.Soporte;
import com.titaniumarmor.soporte_service.repository.SoporteRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader
        implements CommandLineRunner {

    private final SoporteRepository soporteRepository;

    @Override
    public void run(String... args)
            throws Exception {

        if (soporteRepository.count() == 0) {

            soporteRepository.save(
                    Soporte.builder()
                            .usuarioId(1L)
                            .asunto("Problema de pago")
                            .descripcion("No se registró mi compra")
                            .estado("ABIERTO")
                            .fechaCreacion(LocalDateTime.now())
                            .build()
            );

            soporteRepository.save(
                    Soporte.builder()
                            .usuarioId(2L)
                            .asunto("Error en despacho")
                            .descripcion("No llegó el pedido")
                            .estado("CERRADO")
                            .fechaCreacion(LocalDateTime.now())
                            .build()
            );
        }
    }
}