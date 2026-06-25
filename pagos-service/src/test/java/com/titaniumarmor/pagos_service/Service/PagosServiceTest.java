package com.titaniumarmor.pagos_service.Service;

import com.titaniumarmor.pagos_service.exception.ResourceNotFoundException;
import com.titaniumarmor.pagos_service.model.Pago;
import com.titaniumarmor.pagos_service.repository.PagoRepository;
import com.titaniumarmor.pagos_service.service.PagoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagosServiceTest {

    @InjectMocks
    private PagoService pagoService;

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private WebClient webClient;

    @Test
    public void testListar() {

        List<Pago> lista = List.of(
                Pago.builder()
                        .id(1L)
                        .ventaId(10L)
                        .monto(15000.0)
                        .metodoPago("TARJETA")
                        .estado("APROBADO")
                        .fecha(LocalDateTime.now())
                        .build()
        );

        when(pagoRepository.findAll()).thenReturn(lista);

        List<Pago> resultado = pagoService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {

        Long id = 1L;

        Pago pago = Pago.builder()
                .id(id)
                .ventaId(10L)
                .monto(15000.0)
                .metodoPago("TARJETA")
                .estado("APROBADO")
                .fecha(LocalDateTime.now())
                .build();

        when(pagoRepository.findById(id))
                .thenReturn(Optional.of(pago));

        Pago encontrado = pagoService.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
        assertEquals("APROBADO", encontrado.getEstado());
    }

    @Test
    public void testBuscarPorIdNoExiste() {

        when(pagoRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> pagoService.buscarPorId(1L)
        );
    }

    @Test
    public void testEliminar() {

        Long id = 1L;

        Pago pago = Pago.builder()
                .id(id)
                .estado("APROBADO")
                .build();

        when(pagoRepository.findById(id))
                .thenReturn(Optional.of(pago));

        doNothing().when(pagoRepository).delete(pago);

        pagoService.eliminar(id);

        verify(pagoRepository, times(1))
                .delete(pago);
    }

    @Test
    public void testExists() {

        Long id = 1L;

        when(pagoRepository.existsById(id))
                .thenReturn(true);

        Boolean resultado = pagoService.exists(id);

        assertTrue(resultado);

        verify(pagoRepository, times(1))
                .existsById(id);
    }

    @Test
    public void testEstaAprobado() {

        Long id = 1L;

        Pago pago = Pago.builder()
                .id(id)
                .estado("APROBADO")
                .build();

        when(pagoRepository.findById(id))
                .thenReturn(Optional.of(pago));

        Boolean resultado = pagoService.estaAprobado(id);

        assertTrue(resultado);
    }
}
