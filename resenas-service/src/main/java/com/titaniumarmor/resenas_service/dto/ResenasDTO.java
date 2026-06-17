package com.titaniumarmor.resenas_service.dto;

import com.titaniumarmor.resenas_service.model.Resena;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResenasDTO {
    private Long id;

    private Long productoId;

    private Long usuarioId;

    @Min(1)
    @Max(5)
    private Integer puntuacion;

    @NotBlank
    private String comentario;

    public Resena toModel() {

        return Resena.builder()
                .id(id)
                .productoId(productoId)
                .usuarioId(usuarioId)
                .puntuacion(puntuacion)
                .comentario(comentario)
                .build();
    }

    public static ResenasDTO fromEntity(Resena resena) {

        return ResenasDTO.builder()
                .id(resena.getId())
                .productoId(resena.getProductoId())
                .usuarioId(resena.getUsuarioId())
                .puntuacion(resena.getPuntuacion())
                .comentario(resena.getComentario())
                .build();
    }
}