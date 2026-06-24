package com.titaniumarmor.resenas_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titaniumarmor.resenas_service.controller.ResenasController;
import com.titaniumarmor.resenas_service.dto.ResenasDTO;
import com.titaniumarmor.resenas_service.model.Resena;
import com.titaniumarmor.resenas_service.service.ResenasService;

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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ResenasControllerTest {
        
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private ResenasService resenasService;

    @InjectMocks
    private ResenasController resenasController;

    @BeforeEach
    void setUp() {

    mockMvc = MockMvcBuilders
              .standaloneSetup(resenasController)
              .build();

    objectMapper = new ObjectMapper();
     }
    
     @Test
    public void testListar() throws Exception {
        List<Resena> lista = List.of(
                Resena.builder()
                        .id(1L)
                        .productoId(1L)
                        .usuarioId(10L)
                        .puntuacion(5)
                        .comentario("Excelente")
                        .fechaCreacion(LocalDateTime.now())
                        .build()
        );

        when(resenasService.listar()).thenReturn(lista);

        mockMvc.perform(get("/resenas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].comentario").value("Excelente"));

        verify(resenasService, times(1)).listar();
    }

    @Test
    public void testBuscarPorId() throws Exception {
        Long id = 1L;
        Resena resena = Resena.builder()
                .id(id)
                .productoId(2L)
                .usuarioId(3L)
                .puntuacion(4)
                .comentario("Muy buena")
                .build();

        when(resenasService.buscarPorId(id)).thenReturn(resena);

        mockMvc.perform(get("/resenas/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.comentario").value("Muy buena"));

        verify(resenasService, times(1)).buscarPorId(id);
    }

    @Test
    public void testGuardar() throws Exception {
        ResenasDTO dtoInput = ResenasDTO.builder()
                .productoId(1L)
                .usuarioId(2L)
                .puntuacion(5)
                .comentario("Excelente")
                .build();

        Resena resenaCreada = Resena.builder()
                .id(100L) 
                .productoId(1L)
                .usuarioId(2L)
                .puntuacion(5)
                .comentario("Excelente")
                .build();

        when(resenasService.guardar(any(ResenasDTO.class))).thenReturn(resenaCreada);

        mockMvc.perform(post("/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInput))) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(2L));

        verify(resenasService, times(1)).guardar(any(ResenasDTO.class));
    }

    @Test
    public void testEliminar() throws Exception {
        Long id = 1L;
        doNothing().when(resenasService).eliminar(id);

        mockMvc.perform(delete("/resenas/{id}", id))
                .andExpect(status().isNoContent());

        verify(resenasService, times(1)).eliminar(id);
    }
}
