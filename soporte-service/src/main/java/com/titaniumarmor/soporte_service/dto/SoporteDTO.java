package com.titaniumarmor.soporte_service.dto;

import com.titaniumarmor.soporte_service.model.Soporte;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoporteDTO {
    private Long usuarioId;

    @NotBlank
    private String asunto;

    @NotBlank
    private String descripcion;
    
    public Soporte toModel() {

        return Soporte.builder()
                .usuarioId(usuarioId)
                .asunto(asunto)
                .descripcion(descripcion)
                .build();
    }

    public static SoporteDTO fromEntity(Soporte soporte) {

        return SoporteDTO.builder()
                .usuarioId(soporte.getUsuarioId())
                .asunto(soporte.getAsunto())
                .descripcion(soporte.getDescripcion())
                .build();
    }
}
