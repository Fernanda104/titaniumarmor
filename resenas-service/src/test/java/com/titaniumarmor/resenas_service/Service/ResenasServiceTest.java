package com.titaniumarmor.resenas_service.Service;

import com.titaniumarmor.resenas_service.dto.ResenasDTO;
import com.titaniumarmor.resenas_service.exception.BadRequestException;
import com.titaniumarmor.resenas_service.exception.ResourceNotFoundException;
import com.titaniumarmor.resenas_service.model.Resena;
import com.titaniumarmor.resenas_service.repository.ResenaRepository;
import com.titaniumarmor.resenas_service.service.ResenasService;

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
public class ResenasServiceTest {

    @InjectMocks
    private ResenasService resenasService;

    @Mock
    private ResenaRepository resenaRepository;

    @Test
    public void testListar() {

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

        when(resenaRepository.findAll()).thenReturn(lista);

        List<Resena> resultado = resenasService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(resenaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {

        Long id = 1L;

        Resena resena = Resena.builder()
                .id(id)
                .productoId(2L)
                .usuarioId(3L)
                .puntuacion(4)
                .comentario("Muy buena")
                .build();

        when(resenaRepository.findById(id))
                .thenReturn(Optional.of(resena));

        Resena encontrada = resenasService.buscarPorId(id);

        assertNotNull(encontrada);
        assertEquals(id, encontrada.getId());
    }

    @Test
    public void testBuscarPorIdNoExiste() {

        when(resenaRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> resenasService.buscarPorId(1L)
        );
    }

}