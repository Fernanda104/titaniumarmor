package com.titaniumarmor.soporte_service.service;

import com.titaniumarmor.soporte_service.dto.SoporteDTO;
import com.titaniumarmor.soporte_service.exception.BadRequestException;
import com.titaniumarmor.soporte_service.exception.ResourceNotFoundException;
import com.titaniumarmor.soporte_service.model.Soporte;
import com.titaniumarmor.soporte_service.repository.SoporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoporteService {
    
    private final SoporteRepository soporteRepository;

    public List<Soporte> listar() {
        log.info("Consultando lista completa de tickets de soporte");
        return soporteRepository.findAll();
    }

    public Soporte buscarPorId(Long id) {
        log.info("Buscando ticket de soporte con ID: {}", id);

        return soporteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ticket de soporte con ID {} no encontrado", id);
                    return new ResourceNotFoundException("Ticket de soporte no encontrado");
                });
    }

    public Soporte guardar(SoporteDTO dto) {

            log.info(
                    "Registrando ticket de soporte para usuario {}",
                    dto.getUsuarioId());

            Soporte soporte = dto.toModel();

            soporte.setEstado("ABIERTO");

            soporte.setFechaCreacion(
                    LocalDateTime.now());

            Soporte guardado =
                    soporteRepository.save(soporte);

            log.info(
                    "Ticket registrado correctamente ID={}",
                    guardado.getId());

            return guardado;}

        public Soporte actualizar(Long id, SoporteDTO dto) {

            log.info(
                "Actualizando ticket {}",
                id);

        Soporte soporte =
                buscarPorId(id);

        if ("CERRADO".equalsIgnoreCase(
                soporte.getEstado())) {

            throw new BadRequestException(
                    "No se puede modificar un ticket cerrado"
            );
        }

        soporte.setAsunto(
                dto.getAsunto());

        soporte.setDescripcion(
                dto.getDescripcion());

        Soporte actualizado =
                soporteRepository.save(
                        soporte);

        log.info(
                "Ticket actualizado correctamente");

        return actualizado;
    }

    public void eliminar(Long id) {

        log.info("Intentando eliminar ticket de soporte ID: {}", id);

        Soporte soporte = buscarPorId(id);
        soporteRepository.delete(soporte);
        log.info("Ticket de soporte ID {} eliminado correctamente", id);
    }

    public boolean exists(Long id) {

        boolean existe = soporteRepository.existsById(id);

        log.info(
                "Verificación de existencia para ticket de soporte ID {}: {}",
                id,
                existe);

        return existe;
    }

    public List<Soporte> buscarPorUsuario(Long usuarioId) {

        log.info("Buscando tickets de soporte del usuario {}", usuarioId);

        return soporteRepository.findByUsuarioId(usuarioId);
    }
}
