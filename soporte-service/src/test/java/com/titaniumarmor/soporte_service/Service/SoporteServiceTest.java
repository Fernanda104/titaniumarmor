package com.titaniumarmor.soporte_service.Service;

import com.titaniumarmor.soporte_service.dto.SoporteDTO;
import com.titaniumarmor.soporte_service.exception.ResourceNotFoundException;
import com.titaniumarmor.soporte_service.model.Soporte;
import com.titaniumarmor.soporte_service.repository.SoporteRepository;
import com.titaniumarmor.soporte_service.service.SoporteService;

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

public class SoporteServiceTest {

    @InjectMocks
    private SoporteService soporteService;

    @Mock
    private SoporteRepository soporteRepository;

    @Test
    void testListar() {

        List<Soporte> lista = List.of(
                Soporte.builder()
                        .id(1L)
                        .usuarioId(10L)
                        .asunto("Problema pago")
                        .descripcion("No se procesó la compra")
                        .estado("ABIERTO")
                        .fechaCreacion(LocalDateTime.now())
                        .build()
        );

        when(soporteRepository.findAll())
                .thenReturn(lista);

        List<Soporte> resultado =
                soporteService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(soporteRepository, times(1))
                .findAll();
    }

    @Test
    void testBuscarPorId() {

        Long id = 1L;

        Soporte soporte =
                Soporte.builder()
                        .id(id)
                        .usuarioId(10L)
                        .asunto("Problema pago")
                        .descripcion("No se procesó la compra")
                        .estado("ABIERTO")
                        .fechaCreacion(LocalDateTime.now())
                        .build();

        when(soporteRepository.findById(id))
                .thenReturn(Optional.of(soporte));

        Soporte encontrado =
                soporteService.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
        assertEquals(
                "ABIERTO",
                encontrado.getEstado()
        );
    }

    @Test
    void testBuscarPorIdNoExiste() {

        when(soporteRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> soporteService.buscarPorId(1L)
        );
    }

    @Test
    void testGuardar() {

        SoporteDTO dto =
                SoporteDTO.builder()
                        .usuarioId(10L)
                        .asunto("Problema pago")
                        .descripcion("No se procesó la compra")
                        .build();

        Soporte soporteGuardado =
                Soporte.builder()
                        .id(1L)
                        .usuarioId(10L)
                        .asunto(dto.getAsunto())
                        .descripcion(dto.getDescripcion())
                        .estado("ABIERTO")
                        .fechaCreacion(LocalDateTime.now())
                        .build();

        when(soporteRepository.save(any(
                Soporte.class
        ))).thenReturn(soporteGuardado);

        Soporte resultado =
                soporteService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(
                "ABIERTO",
                resultado.getEstado()
        );

        verify(soporteRepository, times(1))
                .save(any(Soporte.class));
    }

    @Test
    void testEliminar() {

        Long id = 1L;

        Soporte soporte =
                Soporte.builder()
                        .id(id)
                        .estado("ABIERTO")
                        .build();

        when(soporteRepository.findById(id))
                .thenReturn(Optional.of(soporte));

        doNothing()
                .when(soporteRepository)
                .delete(soporte);

        soporteService.eliminar(id);

        verify(soporteRepository, times(1))
                .delete(soporte);
    }
}