package com.titaniumarmor.promociones_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.titaniumarmor.promociones_service.dto.PromocionDTO;
import com.titaniumarmor.promociones_service.exception.ResourceNotFoundException;
import com.titaniumarmor.promociones_service.model.Promocion;
import com.titaniumarmor.promociones_service.repository.PromocionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromocionService {
    private final PromocionRepository promocionRepository;

    public List<Promocion> listar(){

        log.info("Listando promociones");
        
        return promocionRepository.findAll();
    }

    public Promocion buscarPorId(Long id){

        log.info("Buscando promocion id={}", id);

        return promocionRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Promoción no encontrada"
                    ));
    }

    public Promocion guardar(PromocionDTO dto){

        log.info("Creando promoción");

        Promocion promocion = new Promocion();

        promocion.setNombre(dto.getNombre());
        promocion.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        promocion.setFechaInicio(dto.getFechaInicio());
        promocion.setFechaFin(dto.getFechaFin());
        promocion.setActiva(dto.getActiva());
        
        return promocionRepository.save(promocion);
    }

     public Promocion actualizar(
            Long id,
            PromocionDTO dto
    ) {

        Promocion promocion = buscarPorId(id);

        promocion.setNombre(dto.getNombre());
        promocion.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        promocion.setFechaInicio(dto.getFechaInicio());
        promocion.setFechaFin(dto.getFechaFin());
        promocion.setActiva(dto.getActiva());

        log.info("Actualizando promoción id={}", id);

        return promocionRepository.save(promocion);
    }

     public void eliminar(Long id) {

        Promocion promocion = buscarPorId(id);

        log.info("Eliminando promoción id={}", id);

        promocionRepository.delete(promocion);
    }
}
