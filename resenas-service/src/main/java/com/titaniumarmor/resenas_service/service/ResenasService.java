package com.titaniumarmor.resenas_service.service;

import com.titaniumarmor.resenas_service.dto.ResenasDTO;
import com.titaniumarmor.resenas_service.exception.BadRequestException;
import com.titaniumarmor.resenas_service.exception.ResourceNotFoundException;
import com.titaniumarmor.resenas_service.model.Resena;
import com.titaniumarmor.resenas_service.repository.ResenaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResenasService {

    private final ResenaRepository resenaRepository;

    public List<Resena> listar() {
        log.info("Consultando lista completa de reseñas");
        return resenaRepository.findAll();
    }

    public Resena buscarPorId(Long id) {
        log.info("Buscando reseña con ID: {}", id);

        return resenaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reseña con ID {} no encontrada", id);
                    return new ResourceNotFoundException("Reseña no encontrada");
                });
    }

    public Resena guardar(ResenasDTO dto) {

        log.info("Intentando registrar reseña para usuario {} y producto {}",
                dto.getProductoId(),
                dto.getUsuarioId());

        if (resenaRepository.existsByProductoIdAndUsuarioId(
                dto.getProductoId(),
                dto.getUsuarioId())) {

            log.warn("El usuario {} ya registró una reseña para el producto {}",
                dto.getProductoId(),
                dto.getUsuarioId());

            throw new BadRequestException(
                    "El usuario ya registró una reseña para este producto");
        }

        if (dto.getPuntuacion() < 1 || dto.getPuntuacion() > 5) {
            throw new BadRequestException(
                    "La puntuación debe estar entre 1 y 5");
        }

        Resena resena = dto.toModel();
        Resena guardada = resenaRepository.save(resena);

        log.info("Reseña creada exitosamente con ID: {}",
                guardada.getId());

        return guardada;
    }

    public Resena actualizar(Long id, ResenasDTO dto) {

        log.info("Iniciando actualización de reseña ID: {}", id);

        Resena resena = buscarPorId(id);

        if (dto.getPuntuacion() < 1 || dto.getPuntuacion() > 5) {
            throw new BadRequestException(
                    "La puntuación debe estar entre 1 y 5");
        }

        resena.setPuntuacion(dto.getPuntuacion());
        resena.setComentario(dto.getComentario());

        Resena actualizada = resenaRepository.save(resena);

        log.info("Reseña ID {} actualizada correctamente", id);

        return actualizada;
    }

    public void eliminar(Long id) {

        log.info("Intentando eliminar reseña ID: {}", id);

        Resena resena = buscarPorId(id);

        resenaRepository.delete(resena);

        log.info("Reseña ID {} eliminada correctamente", id);
    }

    public boolean exists(Long id) {

        boolean existe = resenaRepository.existsById(id);

        log.info(
                "Verificación de existencia para reseña ID {}: {}",
                id,
                existe
        );

        return existe;
    }

    public List<Resena> buscarPorProducto(Long productoId) {

        log.info("Buscando reseñas del producto {}", productoId);

        return resenaRepository.findByProductoId(productoId);
    }

    public List<Resena> buscarPorUsuario(Long usuarioId) {

        log.info("Buscando reseñas del usuario {}", usuarioId);

        return resenaRepository.findByUsuarioId(usuarioId);
    }

   public Double promedioProducto(Long productoId) {

    log.info("Calculando promedio de calificaciones para el producto ID: {}", productoId);

    Double promedio = resenaRepository.promedioProducto(productoId);

    if (promedio == null) {
        log.warn("El producto ID {} aún no tiene reseñas", productoId);
        return 0.0;
    }

    promedio = Math.round(promedio * 10.0) / 10.0;

    log.info("Promedio calculado para el producto ID {}: {}", productoId, promedio);

    return promedio;
}
}