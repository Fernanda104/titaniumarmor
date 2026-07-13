package com.titaniumarmor.soporte_service.controller;

import com.titaniumarmor.soporte_service.dto.SoporteDTO;
import com.titaniumarmor.soporte_service.model.Soporte;
import com.titaniumarmor.soporte_service.service.SoporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soporte")
@RequiredArgsConstructor
public class SoporteController {

    private final SoporteService soporteService;

    @GetMapping
    public ResponseEntity<List<Soporte>> listar() {

        return ResponseEntity.ok(
                soporteService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Soporte> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                soporteService.buscarPorId(id)
        );
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> exists(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                soporteService.exists(id)
        );
    }

    @PostMapping
    public ResponseEntity<SoporteDTO> guardar(
            @Valid @RequestBody SoporteDTO dto
    ) {

        Soporte soporte = soporteService.guardar(dto);

        return ResponseEntity.ok(
                SoporteDTO.fromEntity(soporte)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoporteDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SoporteDTO dto
    ) {

        Soporte soporte = soporteService.actualizar(id, dto);

        return ResponseEntity.ok(
                SoporteDTO.fromEntity(soporte)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        soporteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Soporte  >> buscarPorUsuario(
            @PathVariable Long usuarioId
    ) {

        return ResponseEntity.ok(
                soporteService  .buscarPorUsuario(usuarioId)
        );
    }
}
