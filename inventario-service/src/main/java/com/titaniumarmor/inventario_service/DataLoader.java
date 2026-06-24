package com.titaniumarmor.inventario_service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.titaniumarmor.inventario_service.model.Inventario;
import com.titaniumarmor.inventario_service.repository.InventarioRepository;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Autowired
    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) throws Exception {

          if (inventarioRepository.count() == 0) {
    
    }
        Random random = new Random();

        String[] ubicaciones = {
                "Bodega Ropa Deportiva",
                "Bodega Calzado",
                "Bodega Suplementos",
                "Bodega Accesorios"
        };

        for (long productoId = 1; productoId <= 8; productoId++) {

            Inventario inventario = new Inventario();

            inventario.setProductoId(productoId);
            inventario.setStock(random.nextInt(5, 60));
            inventario.setStockMinimo(random.nextInt(3, 10));
            inventario.setUbicacion(
                    ubicaciones[random.nextInt(ubicaciones.length)]
            );

            inventarioRepository.save(inventario);
        }
    }
}