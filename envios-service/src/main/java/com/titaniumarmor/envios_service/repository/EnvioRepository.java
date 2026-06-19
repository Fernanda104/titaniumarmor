package com.titaniumarmor.envios_service.repository;

import com.titaniumarmor.envios_service.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByPagoId(Long pagoId);
    
    List<Envio> findByEstado(String estado);
}