package com.titaniumarmor.ventas_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.titaniumarmor.ventas_service.model.DetalleVenta;
import com.titaniumarmor.ventas_service.model.Venta;
import com.titaniumarmor.ventas_service.repository.VentaRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private VentaRepository ventaRepository;


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
    
   for (int i = 0; i < 5; i++) {

            Venta venta = new Venta();

            venta.setUsuarioId((long) random.nextInt(1, 4));
            venta.setFecha(LocalDateTime.now().minusDays(random.nextInt(1, 30)));
            venta.setEstado("COMPLETADA");

            List<DetalleVenta> detalles = new ArrayList<>();

            double totalVenta = 0.0;

            int cantidadDetalles = random.nextInt(1, 4);

            for (int j = 0; j < cantidadDetalles; j++) {

                DetalleVenta detalle = new DetalleVenta();

                Long productoId = (long) random.nextInt(1, 5);
                int cantidad = random.nextInt(1, 4);
                double precio = faker.number().randomDouble(0, 10000, 100000);
                double subtotal = precio * cantidad;

                detalle.setProductoId(productoId);
                detalle.setCantidad(cantidad);
                detalle.setPrecio(precio);
                detalle.setSubtotal(subtotal);

                detalle.setVenta(venta);

                detalles.add(detalle);

                totalVenta += subtotal;
            }

            venta.setDetalles(detalles);
            venta.setTotal(totalVenta);

            ventaRepository.save(venta);
        }
    }
}
