package com.titaniumarmor.envios_service.controller;

import com.titaniumarmor.envios_service.dto.EnvioDTO;
import com.titaniumarmor.envios_service.model.Envio;
import com.titaniumarmor.envios_service.service.EnviosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
@RequiredArgsConstructor
public class EnvioController {

   private final EnviosService enviosService;

   @GetMapping
   public ResponseEntity<List<Envio>> listar() {

       return ResponseEntity.ok(enviosService.listar());
   }

   @GetMapping("/{id}")
   public ResponseEntity<Envio> buscarPorId(
           @PathVariable Long id
   ) {

       return ResponseEntity.ok(enviosService.buscarPorId(id));
   }

   @GetMapping("/{id}/exists")
   public ResponseEntity<Boolean> exists(
           @PathVariable Long id
   ) {

       return ResponseEntity.ok(enviosService.exists(id));
   }

   @PostMapping
   public ResponseEntity<EnvioDTO> guardar(
           @Valid @RequestBody EnvioDTO dto
   ) {

       Envio envio = enviosService.guardar(dto);

       return ResponseEntity.ok(
               EnvioDTO.fromEntity(envio)
       );
   }

   @PutMapping("/{id}")
   public ResponseEntity<EnvioDTO> actualizar(
           @PathVariable Long id,
           @Valid @RequestBody EnvioDTO dto
   ) {

       Envio envio = enviosService.actualizar(id, dto);

       return ResponseEntity.ok(
               EnvioDTO.fromEntity(envio)
       );
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> eliminar(
           @PathVariable Long id
   ) {

       enviosService.eliminar(id);

       return ResponseEntity.noContent().build();
   }

   @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Envio>> buscarPorEstado(
        @PathVariable String estado) {

        return ResponseEntity.ok(
                enviosService.buscarPorEstado(estado)
        );
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<Envio>> buscarPorVentaId(
            @PathVariable Long ventaId) {

        return ResponseEntity.ok(
                enviosService.buscarPorVentaId(ventaId)
        );
}
}