package com.titaniumarmor.inventario_service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.titaniumarmor.inventario_service.controller.InventarioControllerV2;
import com.titaniumarmor.inventario_service.model.Inventario;



@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>>{


   
    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(inventario,
                linkTo(methodOn(InventarioControllerV2.class).obtenerInventario(inventario.getId())).withSelfRel(),
                linkTo(methodOn(InventarioControllerV2.class).listarInventarios()).withRel("inventarios"));  
            }
}
