package com.titaniumarmor.soporte_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumarmor.soporte_service.controller.SoporteController;
import com.titaniumarmor.soporte_service.dto.SoporteDTO;
import com.titaniumarmor.soporte_service.model.Soporte;
import com.titaniumarmor.soporte_service.service.SoporteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SoporteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SoporteService soporteService;

    @InjectMocks
    private SoporteController soporteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(soporteController)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(
                new JavaTimeModule()
        );
    }

    @Test
    void testListar() throws Exception {

        List<Soporte> lista = List.of(

                Soporte.builder()
                        .id(1L)
                        .usuarioId(10L)
                        .asunto("Problema de pago")
                        .descripcion("No se registró la compra")
                        .estado("ABIERTO")
                        .fechaCreacion(LocalDateTime.now())
                        .build()
        );

        when(soporteService.listar())
                .thenReturn(lista);

        mockMvc.perform(
                        get("/soporte")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$[0].id")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$[0].estado")
                                .value("ABIERTO")
                );

        verify(
                soporteService,
                times(1)
        ).listar();
    }

    @Test
    void testBuscarPorId()
            throws Exception {

        Long id = 1L;

        Soporte soporte =
                Soporte.builder()
                        .id(id)
                        .usuarioId(10L)
                        .asunto("Problema de pago")
                        .descripcion("No se registró la compra")
                        .estado("ABIERTO")
                        .fechaCreacion(LocalDateTime.now())
                        .build();

        when(
                soporteService.buscarPorId(id)
        ).thenReturn(soporte);

        mockMvc.perform(
                        get("/soporte/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(id)
                )
                .andExpect(
                        jsonPath("$.estado")
                                .value("ABIERTO")
                );

        verify(
                soporteService
        ).buscarPorId(id);
    }

    @Test
        void testGuardar() throws Exception {

    SoporteDTO dto =
            SoporteDTO.builder()
                    .usuarioId(10L)
                    .asunto("Problema de pago")
                    .descripcion("No se registró la compra")
                    .build();

    Soporte soporte =
            Soporte.builder()
                    .id(1L)
                    .usuarioId(10L)
                    .asunto(dto.getAsunto())
                    .descripcion(dto.getDescripcion())
                    .estado("ABIERTO")
                    .fechaCreacion(LocalDateTime.now())
                    .build();

    when(
            soporteService.guardar(
                    any(SoporteDTO.class)
            )
    ).thenReturn(soporte);

    mockMvc.perform(
                    post("/soporte")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    objectMapper.writeValueAsString(dto)
                            )
            )
            .andExpect(status().isOk())
            .andExpect(
                    jsonPath("$.usuarioId")
                            .value(10L)
            )
            .andExpect(
                    jsonPath("$.asunto")
                            .value("Problema de pago")
            )
            .andExpect(
                    jsonPath("$.descripcion")
                            .value("No se registró la compra")
            );

    verify(
            soporteService,
            times(1)
    ).guardar(
            any(SoporteDTO.class)
    );
}

    @Test
    void testActualizar()
            throws Exception {

        Long id = 1L;

        SoporteDTO dto =
                SoporteDTO.builder()
                        .usuarioId(10L)
                        .asunto("Problema actualizado")
                        .descripcion("Nueva descripción")
                        .build();

        Soporte soporte =
                Soporte.builder()
                        .id(id)
                        .usuarioId(10L)
                        .asunto(dto.getAsunto())
                        .descripcion(dto.getDescripcion())
                        .estado("ABIERTO")
                        .build();

        when(
                soporteService.actualizar(
                        eq(id),
                        any(SoporteDTO.class)
                )
        ).thenReturn(soporte);

        mockMvc.perform(
                        put("/soporte/{id}", id)
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper
                                                .writeValueAsString(dto)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.asunto")
                                .value(
                                        "Problema actualizado"
                                )
                );

        verify(
                soporteService
        ).actualizar(
                eq(id),
                any(SoporteDTO.class)
        );
    }

    @Test
    void testEliminar()
            throws Exception {

        Long id = 1L;

        doNothing()
                .when(soporteService)
                .eliminar(id);

        mockMvc.perform(
                        delete(
                                "/soporte/{id}",
                                id
                        )
                )
                .andExpect(
                        status()
                                .isNoContent()
                );

        verify(
                soporteService
        ).eliminar(id);
    }
}