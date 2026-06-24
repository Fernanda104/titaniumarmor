package com.titaniumarmor.ventas_service.dto;

import com.titaniumarmor.ventas_service.model.Venta.VentaBuilder;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private Double precio;
    
    public static VentaBuilder builder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'builder'");
    }
}
