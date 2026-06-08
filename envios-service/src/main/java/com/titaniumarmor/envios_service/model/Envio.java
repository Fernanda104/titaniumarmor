package com.titaniumarmor.envios_service.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "envios")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ventaId;

    private String direccionEntrega;

    private String empresaTransporte;

    private String numeroSeguimiento;

    private String estado;
}