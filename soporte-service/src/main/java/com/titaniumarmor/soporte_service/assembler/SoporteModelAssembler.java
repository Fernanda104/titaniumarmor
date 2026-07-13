package com.titaniumarmor.soporte_service.assembler;

import com.titaniumarmor.soporte_service.controller.SoporteControllerV2;
import com.titaniumarmor.soporte_service.model.Soporte;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SoporteModelAssembler
        implements RepresentationModelAssembler<Soporte, EntityModel<Soporte>> {

    @Override
    public EntityModel<Soporte> toModel(Soporte soporte) {
        return EntityModel.of(
                soporte,
                linkTo(
                        methodOn(SoporteControllerV2.class)
                                .obtenerSoporte(soporte.getId())
                ).withSelfRel(),
                linkTo(
                        methodOn(SoporteControllerV2.class)
                                .listarSoportes()
                ).withRel("soportes")
        );
    }
}
