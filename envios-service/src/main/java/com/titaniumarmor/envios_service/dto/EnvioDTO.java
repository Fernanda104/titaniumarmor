package com.titaniumarmor.envios_service.dto;

import com.titaniumarmor.envios_service.model.Envio;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvioDTO {
    private Long id;

    private Long ventaId;

    @NotBlank
    private String direccionEntrega;

    @NotBlank
    private String empresaTransporte;

    @NotBlank
    private String numeroSeguimiento;

    @NotBlank
    private String estado;

    public Envio toModel() {
        
        return Envio.builder()
                .id(id)
                .ventaId(ventaId)
                .direccionEntrega(direccionEntrega)
                .empresaTransporte(empresaTransporte)
                .numeroSeguimiento(numeroSeguimiento)
                .estado(estado)
                .build();
    }

    public static EnvioDTO fromEntity(Envio envio) {
        return EnvioDTO.builder()
                .id(envio.getId())
                .ventaId(envio.getVentaId())
                .direccionEntrega(envio.getDireccionEntrega())
                .empresaTransporte(envio.getEmpresaTransporte())
                .numeroSeguimiento(envio.getNumeroSeguimiento())
                .estado(envio.getEstado())
                .build();
    }
}