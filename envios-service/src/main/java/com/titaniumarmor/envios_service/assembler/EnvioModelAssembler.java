package com.titaniumarmor.envios_service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.titaniumarmor.envios_service.controller.EnvioControllerV2;
import com.titaniumarmor.envios_service.model.Envio;





@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>>{


   
    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioControllerV2.class).obtenerEnvio(envio.getId())).withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).listarEnvios()).withRel("envios"));  
            }
}
