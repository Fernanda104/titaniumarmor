package com.titaniumarmor.ventas_service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.titaniumarmor.ventas_service.controller.VentaControllerV2;
import com.titaniumarmor.ventas_service.model.Venta;





@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>>{


   
    @Override
    public EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(venta,
                linkTo(methodOn(VentaControllerV2.class).obtenerVenta(venta.getId())).withSelfRel(),
                linkTo(methodOn(VentaControllerV2.class).listarVentas()).withRel("ventas"));  
            }
}
