package com.titaniumarmor.promociones_service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.titaniumarmor.promociones_service.controller.PromocionControllerV2;
import com.titaniumarmor.promociones_service.model.Promocion;


@Component
public class PromocionModelAssembler implements RepresentationModelAssembler<Promocion, EntityModel<Promocion>>{


   
    @Override
    public EntityModel<Promocion> toModel(Promocion promocion) {
        return EntityModel.of(promocion,
                linkTo(methodOn(PromocionControllerV2.class).obtenerPromocion(promocion.getId())).withSelfRel(),
                linkTo(methodOn(PromocionControllerV2.class).listarPromociones()).withRel("promociones"));  
            }
}

