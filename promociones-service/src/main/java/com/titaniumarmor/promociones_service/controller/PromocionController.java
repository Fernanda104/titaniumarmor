package com.titaniumarmor.promociones_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.titaniumarmor.promociones_service.dto.PromocionDTO;
import com.titaniumarmor.promociones_service.model.Promocion;
import com.titaniumarmor.promociones_service.service.PromocionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/promociones")
@RequiredArgsConstructor
public class PromocionController {
    private final PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<Promocion>> listar() {

        return ResponseEntity.ok(
                promocionService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promocion> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                promocionService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Promocion> guardar(
            @Valid @RequestBody PromocionDTO dto
    ) {

        return ResponseEntity.ok(
                promocionService.guardar(dto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promocion> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PromocionDTO dto
    ) {

        return ResponseEntity.ok(
                promocionService.actualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        promocionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

}
