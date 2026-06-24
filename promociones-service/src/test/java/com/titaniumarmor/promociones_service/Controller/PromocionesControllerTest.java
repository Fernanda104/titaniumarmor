package com.titaniumarmor.promociones_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumarmor.promociones_service.controller.PromocionController;
import com.titaniumarmor.promociones_service.dto.PromocionDTO;
import com.titaniumarmor.promociones_service.model.Promocion;
import com.titaniumarmor.promociones_service.service.PromocionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromocionController.class)
public class PromocionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PromocionService promocionService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    public void testListar() throws Exception {

        List<Promocion> lista = List.of(
                new Promocion(
                        1L,
                        "Cyber Day",
                        20.0,
                        LocalDate.now(),
                        LocalDate.now().plusDays(5),
                        true
                )
        );

        when(promocionService.listar()).thenReturn(lista);

        mockMvc.perform(get("/promociones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Cyber Day"))
                .andExpect(jsonPath("$[0].porcentajeDescuento").value(20.0));

        verify(promocionService, times(1)).listar();
    }

    @Test
    public void testBuscarPorId() throws Exception {

        Long id = 1L;

        Promocion promocion = new Promocion(
                id,
                "Cyber Day",
                20.0,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                true
        );

        when(promocionService.buscarPorId(id)).thenReturn(promocion);

        mockMvc.perform(get("/promociones/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Cyber Day"));

        verify(promocionService).buscarPorId(id);
    }

    @Test
    public void testGuardar() throws Exception {

        PromocionDTO dto = new PromocionDTO();

        dto.setNombre("Cyber Day");
        dto.setPorcentajeDescuento(20.0);
        dto.setFechaInicio(LocalDate.now());
        dto.setFechaFin(LocalDate.now().plusDays(5));
        dto.setActiva(true);

        Promocion promocion = new Promocion(
                1L,
                dto.getNombre(),
                dto.getPorcentajeDescuento(),
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getActiva()
        );

        when(promocionService.guardar(any(PromocionDTO.class)))
                .thenReturn(promocion);

        mockMvc.perform(post("/promociones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cyber Day"));

        verify(promocionService).guardar(any(PromocionDTO.class));
    }

    @Test
    public void testActualizar() throws Exception {

        Long id = 1L;

        PromocionDTO dto = new PromocionDTO();

        dto.setNombre("Black Friday");
        dto.setPorcentajeDescuento(30.0);
        dto.setFechaInicio(LocalDate.now());
        dto.setFechaFin(LocalDate.now().plusDays(7));
        dto.setActiva(true);

        Promocion promocion = new Promocion(
                id,
                dto.getNombre(),
                dto.getPorcentajeDescuento(),
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getActiva()
        );

        when(promocionService.actualizar(eq(id), any(PromocionDTO.class)))
                .thenReturn(promocion);

        mockMvc.perform(put("/promociones/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Black Friday"))
                .andExpect(jsonPath("$.porcentajeDescuento").value(30.0));

        verify(promocionService).actualizar(eq(id), any(PromocionDTO.class));
    }

    @Test
    public void testEliminar() throws Exception {

        Long id = 1L;

        doNothing().when(promocionService).eliminar(id);

        mockMvc.perform(delete("/promociones/{id}", id))
                .andExpect(status().isNoContent());

        verify(promocionService).eliminar(id);
    }
}