package com.titaniumarmor.soporte_service.repository;

import com.titaniumarmor.soporte_service.model.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoporteRepository extends JpaRepository<Soporte, Long> {
    List<Soporte> findByUsuarioId(Long usuarioId);

    List<Soporte> findByEstado(String estado);
}
