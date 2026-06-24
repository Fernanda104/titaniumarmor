package com.titaniumarmor.promociones_service.Service;

import com.titaniumarmor.promociones_service.exception.ResourceNotFoundException;
import com.titaniumarmor.promociones_service.model.Promocion;
import com.titaniumarmor.promociones_service.repository.PromocionRepository;
import com.titaniumarmor.promociones_service.service.PromocionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromocionesServiceTest {

    @InjectMocks
    private PromocionService promocionesService;

    @Mock
    private PromocionRepository promocionRepository;

    @Test
    public void testListar() {

        List<Promocion> lista = List.of(
                Promocion.builder()
                        .id(1L)
                        .nombre("Cyber Day")
                        .porcentajeDescuento(20.0)
                        .fechaInicio(LocalDate.now())
                        .fechaFin(LocalDate.now().plusDays(5))
                        .activa(true)
                        .build()
        );

        when(promocionRepository.findAll()).thenReturn(lista);

        List<Promocion> resultado = promocionesService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(promocionRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {

        Long id = 1L;

        Promocion promocion = Promocion.builder()
                .id(id)
                .nombre("Cyber Day")
                .porcentajeDescuento(20.0)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(5))
                .activa(true)
                .build();

        when(promocionRepository.findById(id))
                .thenReturn(Optional.of(promocion));

        Promocion encontrada = promocionesService.buscarPorId(id);

        assertNotNull(encontrada);
        assertEquals(id, encontrada.getId());
        assertEquals("Cyber Day", encontrada.getNombre());
    }

    @Test
    public void testBuscarPorIdNoExiste() {

        when(promocionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> promocionesService.buscarPorId(1L)
        );
    }

    @Test
    public void testEliminar() {

        Long id = 1L;

        Promocion promocion = Promocion.builder()
                .id(id)
                .nombre("Oferta")
                .build();

        when(promocionRepository.findById(id))
                .thenReturn(Optional.of(promocion));

        doNothing().when(promocionRepository).delete(promocion);

        promocionesService.eliminar(id);

        verify(promocionRepository, times(1)).delete(promocion);
    }

}