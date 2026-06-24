package com.titaniumarmor.pagos_service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.titaniumarmor.pagos_service.controller.PagoControllerV2;
import com.titaniumarmor.pagos_service.model.Pago;





@Component
public class PagosModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>>{


   
    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).obtenerPago(pago.getId())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).listarPagos()).withRel("pagos"));  
            }
}
