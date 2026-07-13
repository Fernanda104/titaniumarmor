package com.titaniumarmor.soporte_service.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets_soporte")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Soporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private String asunto;

    private String descripcion;

    private String estado;

    private LocalDateTime fechaCreacion;
}
