package com.titaniumarmor.inventario_service.Service;

import com.titaniumarmor.inventario_service.exception.ResourceNotFoundException;
import com.titaniumarmor.inventario_service.model.Inventario;
import com.titaniumarmor.inventario_service.repository.InventarioRepository;
import com.titaniumarmor.inventario_service.service.InventarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @InjectMocks
    private InventarioService inventarioService;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private WebClient webClient;

    @Test
    public void testListar() {

        List<Inventario> lista = List.of(
                Inventario.builder()
                        .id(1L)
                        .productoId(10L)
                        .stock(20)
                        .stockMinimo(5)
                        .ubicacion("Bodega A")
                        .build()
        );

        when(inventarioRepository.findAll()).thenReturn(lista);

        List<Inventario> resultado = inventarioService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {

        Long id = 1L;

        Inventario inventario = Inventario.builder()
                .id(id)
                .productoId(10L)
                .stock(20)
                .stockMinimo(5)
                .ubicacion("Bodega A")
                .build();

        when(inventarioRepository.findById(id))
                .thenReturn(Optional.of(inventario));

        Inventario encontrado = inventarioService.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
        assertEquals(20, encontrado.getStock());
    }

    @Test
    public void testBuscarPorIdNoExiste() {

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> inventarioService.buscarPorId(1L)
        );
    }

    @Test
    public void testObtenerStock() {

        Inventario inventario = Inventario.builder()
                .id(1L)
                .productoId(10L)
                .stock(25)
                .build();

        when(inventarioRepository.findByProductoId(10L))
                .thenReturn(Optional.of(inventario));

        Integer stock = inventarioService.obtenerStock(10L);

        assertEquals(25, stock);
    }

    @Test
    public void testDisponible() {

        Inventario inventario = Inventario.builder()
                .productoId(10L)
                .stock(5)
                .build();

        when(inventarioRepository.findByProductoId(10L))
                .thenReturn(Optional.of(inventario));

        Boolean disponible = inventarioService.disponible(10L);

        assertTrue(disponible);
    }

    @Test
    public void testEliminar() {

        Long id = 1L;

        Inventario inventario = Inventario.builder()
                .id(id)
                .productoId(10L)
                .build();

        when(inventarioRepository.findById(id))
                .thenReturn(Optional.of(inventario));

        doNothing().when(inventarioRepository)
                .delete(inventario);

        inventarioService.eliminar(id);

        verify(inventarioRepository, times(1))
                .delete(inventario);
    }
}