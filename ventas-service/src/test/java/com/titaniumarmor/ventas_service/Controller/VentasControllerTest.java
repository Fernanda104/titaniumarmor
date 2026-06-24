package com.titaniumarmor.ventas_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumarmor.ventas_service.controller.VentaController;
import com.titaniumarmor.ventas_service.dto.DetalleVentaDTO;
import com.titaniumarmor.ventas_service.dto.VentaDTO;
import com.titaniumarmor.ventas_service.model.Venta;
import com.titaniumarmor.ventas_service.service.VentaService;

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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class VentasControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VentaService ventaService;

    @InjectMocks
    private VentaController ventaController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(ventaController)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testListar() throws Exception {

        List<Venta> lista = List.of(
                Venta.builder()
                        .id(1L)
                        .usuarioId(10L)
                        .estado("COMPLETADO")
                        .total(250.0)
                        .fecha(LocalDateTime.now())
                        .build()
        );

        when(ventaService.listar()).thenReturn(lista);

        mockMvc.perform(get("/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("COMPLETADO"))
                .andExpect(jsonPath("$[0].total").value(250.0));

        verify(ventaService, times(1)).listar();
    }

    @Test
    void testBuscarPorId() throws Exception {

        Long id = 1L;

        Venta venta = Venta.builder()
                .id(id)
                .usuarioId(10L)
                .estado("PENDIENTE")
                .total(100.0)
                .build();

        when(ventaService.buscarPorId(id)).thenReturn(venta);

        mockMvc.perform(get("/ventas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        verify(ventaService).buscarPorId(id);
    }

    @Test
    void testGuardar() throws Exception {

        DetalleVentaDTO detalle = DetalleVentaDTO.builder()
                .productoId(2L)
                .cantidad(3)
                .build();

        VentaDTO dto = VentaDTO.builder()
                .usuarioId(5L)
                .detalles(List.of(detalle))
                .build();

        Venta venta = Venta.builder()
                .id(99L)
                .usuarioId(5L)
                .estado("PENDIENTE")
                .total(300.0)
                .build();

        when(ventaService.guardar(any(VentaDTO.class)))
                .thenReturn(venta);

        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.total").value(300.0));

        verify(ventaService).guardar(any(VentaDTO.class));
    }

    @Test
    void testEliminar() throws Exception {

        Long id = 1L;

        doNothing().when(ventaService).eliminar(id);

        mockMvc.perform(delete("/ventas/{id}", id))
                .andExpect(status().isNoContent());

        verify(ventaService).eliminar(id);
    }
}