package com.titaniumarmor.envios_service.service;

import com.titaniumarmor.envios_service.dto.EnvioDTO;
import com.titaniumarmor.envios_service.exception.ResourceNotFoundException;
import com.titaniumarmor.envios_service.model.Envio;
import com.titaniumarmor.envios_service.repository.EnvioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnviosService {

    private final EnvioRepository envioRepository;
    private final WebClient webClient;

    @Value("${api.venta.exists}")
    private String ventaExistsPath;



    public List<Envio> listar() {
        log.info("Consultando lista completa de envios");
        return envioRepository.findAll();
    }

    public Envio buscarPorId(Long id) {
        log.info("Buscando envio con ID: {}", id);
        return envioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Envio con ID {} no encontrado", id);
                    return new ResourceNotFoundException("Envio no encontrado");
                });
    }

public Envio guardar(EnvioDTO dto) {
    log.info("Intentando registrar nuevo envio para ventaId={}", dto.getVentaId());

    Boolean ventaExiste = webClient.get()
            .uri(String.format(ventaExistsPath, dto.getVentaId()))
            .retrieve()
            .bodyToMono(Boolean.class)
            .block();

    if (Boolean.FALSE.equals(ventaExiste) || ventaExiste == null) {
        throw new ResourceNotFoundException("Venta no existe");
    }

    Envio envio = dto.toModel();
    Envio guardado = envioRepository.save(envio);

    log.info("Envio creado exitosamente con ID: {}", guardado.getId());
    return guardado;
}

    public Envio actualizar(Long id, EnvioDTO dto) {
        log.info("Iniciando actualización para envio ID: {}", id);

        Envio envio = buscarPorId(id);


        envio.setVentaId(dto.getVentaId());
        envio.setDireccionEntrega(dto.getDireccionEntrega());
        envio.setEmpresaTransporte(dto.getEmpresaTransporte());
        envio.setNumeroSeguimiento(dto.getNumeroSeguimiento());
        envio.setEstado(dto.getEstado());

        Envio actualizado = envioRepository.save(envio);
        log.info("Envio ID: {} actualizado correctamente", id);
        
        return actualizado;
    }

    public void eliminar(Long id) {
        log.info("Intentando eliminar envio ID: {}", id);
        Envio envio = buscarPorId(id);
        envioRepository.delete(envio);
        log.info("Envio ID: {} eliminado del sistema", id);
    }

    public boolean exists(Long id) {
        boolean existe = envioRepository.existsById(id);
        log.info("Verificación de existencia para envio ID {}: {}", id, existe);
        return existe;
    }

    public List<Envio> buscarPorVentaId(Long ventaId) {
        log.info("Buscando envio por ID de venta: {}", ventaId);
        return envioRepository.findByVentaId(ventaId);
    }

    public List<Envio> buscarPorEstado(String estado) {
        log.info("Buscando envio por estado: {}", estado);
        return envioRepository.findByEstado(estado);
    }
}