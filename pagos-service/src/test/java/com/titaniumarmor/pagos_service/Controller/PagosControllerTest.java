package com.titaniumarmor.pagos_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumarmor.pagos_service.controller.PagoController;
import com.titaniumarmor.pagos_service.dto.PagoDTO;
import com.titaniumarmor.pagos_service.model.Pago;
import com.titaniumarmor.pagos_service.service.PagoService;

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
public class PagosControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagoService pagoService;

    @InjectMocks
    private PagoController pagoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(pagoController)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testListar() throws Exception {

        List<Pago> lista = List.of(
                Pago.builder()
                        .id(1L)
                        .ventaId(10L)
                        .monto(150000.0)
                        .metodoPago("TARJETA")
                        .estado("APROBADO")
                        .fecha(LocalDateTime.now())
                        .build()
        );

        when(pagoService.listar()).thenReturn(lista);

        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("APROBADO"));

        verify(pagoService, times(1)).listar();
    }

    @Test
    void testBuscarPorId() throws Exception {

        Long id = 1L;

        Pago pago = Pago.builder()
                .id(id)
                .ventaId(10L)
                .monto(150000.0)
                .metodoPago("TARJETA")
                .estado("APROBADO")
                .build();

        when(pagoService.buscarPorId(id)).thenReturn(pago);

        mockMvc.perform(get("/pagos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("APROBADO"));

        verify(pagoService).buscarPorId(id);
    }

    @Test
    void testGuardar() throws Exception {

        PagoDTO dto = new PagoDTO();
        dto.setVentaId(10L);
        dto.setMetodoPago("TARJETA");

        Pago pago = Pago.builder()
                .id(1L)
                .ventaId(10L)
                .monto(150000.0)
                .metodoPago("TARJETA")
                .estado("APROBADO")
                .build();

        when(pagoService.guardar(any(PagoDTO.class)))
                .thenReturn(pago);

        mockMvc.perform(post("/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("APROBADO"));

        verify(pagoService).guardar(any(PagoDTO.class));
    }

    @Test
    void testActualizar() throws Exception {

        Long id = 1L;

        PagoDTO dto = new PagoDTO();
        dto.setMetodoPago("TRANSFERENCIA");
        dto.setEstado("APROBADO");
        dto.setMonto(200000.0);

        Pago pago = Pago.builder()
                .id(id)
                .ventaId(10L)
                .monto(200000.0)
                .metodoPago("TRANSFERENCIA")
                .estado("APROBADO")
                .build();

        when(pagoService.actualizar(eq(id), any(PagoDTO.class)))
                .thenReturn(pago);

        mockMvc.perform(put("/pagos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metodoPago").value("TRANSFERENCIA"))
                .andExpect(jsonPath("$.monto").value(200000.0));

        verify(pagoService).actualizar(eq(id), any(PagoDTO.class));
    }

    @Test
    void testEliminar() throws Exception {

        Long id = 1L;

        doNothing().when(pagoService).eliminar(id);

        mockMvc.perform(delete("/pagos/{id}", id))
                .andExpect(status().isNoContent());

        verify(pagoService).eliminar(id);
    }

}