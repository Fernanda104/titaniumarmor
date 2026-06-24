package com.titaniumarmor.resenas_service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.titaniumarmor.resenas_service.controller.ResenasControllerV2;
import com.titaniumarmor.resenas_service.model.Resena;


@Component
public class ResenaModelAssembler implements RepresentationModelAssembler<Resena, EntityModel<Resena>>{


   
    @Override
    public EntityModel<Resena> toModel(Resena resena) {
        return EntityModel.of(resena,
                linkTo(methodOn(ResenasControllerV2.class).obtenerResena(resena.getId())).withSelfRel(),
                linkTo(methodOn(ResenasControllerV2.class).listarResenas()).withRel("resenas"));  
            }
}
