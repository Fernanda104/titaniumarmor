package com.titaniumarmor.ventas_service.Service;

import com.titaniumarmor.ventas_service.exception.ResourceNotFoundException;
import com.titaniumarmor.ventas_service.model.Venta;
import com.titaniumarmor.ventas_service.repository.VentaRepository;
import com.titaniumarmor.ventas_service.service.VentaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentasServiceTest {

    @InjectMocks
    private VentaService ventasService;

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private WebClient webClient;

    @SuppressWarnings("rawtypes")
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @SuppressWarnings("rawtypes")
    @Mock private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ventasService, "usuarioExistsUrl", "http://localhost/usuarios/%d/exists");
        ReflectionTestUtils.setField(ventasService, "productoUrl", "http://localhost/catalogo/%d");
        ReflectionTestUtils.setField(ventasService, "inventarioDisponibleUrl", "http://localhost/inventario/%d/disponible");
        ReflectionTestUtils.setField(ventasService, "inventarioDescontarUrl", "http://localhost/inventario/descontar");
    }

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

        when(ventaRepository.findById(id)).thenReturn(Optional.of(venta));

        Venta encontrada = ventasService.buscarPorId(id);

        assertNotNull(encontrada);
        assertEquals(id, encontrada.getId());
        assertEquals("PENDIENTE", encontrada.getEstado());
    }

    @Test
    public void testBuscarPorIdNoExiste() {
        Long id = 1L;
        when(ventaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> ventasService.buscarPorId(id)
        );
    }

}
