package com.titaniumarmor.resenas_service.controller;

import com.titaniumarmor.resenas_service.dto.ResenasDTO;
import com.titaniumarmor.resenas_service.model.Resena;
import com.titaniumarmor.resenas_service.service.ResenasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resenas")
@RequiredArgsConstructor
public class ResenasController {

    private final ResenasService resenasService;

    @GetMapping
    public ResponseEntity<List<Resena>> listar() {

        return ResponseEntity.ok(
                resenasService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                resenasService.buscarPorId(id)
        );
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> exists(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                resenasService.exists(id)
        );
    }

    @PostMapping
    public ResponseEntity<ResenasDTO> guardar(
            @Valid @RequestBody ResenasDTO dto
    ) {

        Resena resena = resenasService.guardar(dto);

        return ResponseEntity.ok(
                ResenasDTO.fromEntity(resena)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResenasDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResenasDTO dto
    ) {

        Resena resena = resenasService.actualizar(id, dto);

        return ResponseEntity.ok(
                ResenasDTO.fromEntity(resena)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        resenasService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Resena>> buscarPorProducto(
            @PathVariable Long productoId
    ) {

        return ResponseEntity.ok(
                resenasService.buscarPorProducto(productoId)
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Resena>> buscarPorUsuario(
            @PathVariable Long usuarioId
    ) {

        return ResponseEntity.ok(
                resenasService.buscarPorUsuario(usuarioId)
        );
    }

    @GetMapping("/producto/{productoId}/promedio")
    public ResponseEntity<Double> promedioProducto(
            @PathVariable Long productoId
    ) {

        return ResponseEntity.ok(
                resenasService.promedioProducto(productoId)
        );
    }
}