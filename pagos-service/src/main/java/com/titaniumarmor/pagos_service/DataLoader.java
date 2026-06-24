package com.titaniumarmor.pagos_service;

import com.titaniumarmor.pagos_service.dto.PagoDTO;
import com.titaniumarmor.pagos_service.repository.PagoRepository;
import com.titaniumarmor.pagos_service.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (pagoRepository.count() == 0) {
    }
        Random random = new Random();

        String[] metodosPago = {
                "Tarjeta de Crédito",
                "Tarjeta de Débito",
                "Transferencia Bancaria",
                "Mercado Pago"
        };

        for (long ventaId = 1; ventaId <= 5; ventaId++) {

            PagoDTO dto = new PagoDTO();

            dto.setVentaId(ventaId);
            dto.setMetodoPago(
                    metodosPago[random.nextInt(metodosPago.length)]
            );

            pagoService.guardar(dto);
        }
    }
}