package com.titaniumarmor.ventas_service.Service;

import com.titaniumarmor.ventas_service.exception.ResourceNotFoundException;
import com.titaniumarmor.ventas_service.model.Venta;
import com.titaniumarmor.ventas_service.repository.VentaRepository;
import com.titaniumarmor.ventas_service.service.VentaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentasServiceTest {

    @InjectMocks
    private VentaService ventasService;

    @Mock
    private VentaRepository ventaRepository;

    @Test
    public void testBuscarPorId() {

        Long id = 1L;

        Venta venta = Venta.builder()
                .id(id)
                .usuarioId(10L)
                .estado("PENDIENTE")
                .fecha(LocalDateTime.now())
                .total(150.0)
                .build();

        when(ventaRepository.findById(id))
                .thenReturn(Optional.of(venta));

        Venta encontrada = ventasService.buscarPorId(id);

        assertNotNull(encontrada);
        assertEquals(id, encontrada.getId());
        assertEquals("PENDIENTE", encontrada.getEstado());

        verify(ventaRepository, times(1))
                .findById(id);
    }

    @Test
    public void testBuscarPorIdNoExiste() {

        Long id = 1L;

        when(ventaRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> ventasService.buscarPorId(id)
        );

        verify(ventaRepository, times(1))
                .findById(id);
    }

    @Test
    public void testListar() {

        List<Venta> lista = List.of(
                Venta.builder()
                        .id(1L)
                        .usuarioId(10L)
                        .estado("PENDIENTE")
                        .total(150.0)
                        .build()
        );

        when(ventaRepository.findAll())
                .thenReturn(lista);

        List<Venta> resultado = ventasService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(ventaRepository, times(1))
                .findAll();
    }
}