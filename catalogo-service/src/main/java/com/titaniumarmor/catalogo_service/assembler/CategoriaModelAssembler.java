package com.titaniumarmor.catalogo_service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.titaniumarmor.catalogo_service.controller.CategoriaControllerV2;
import com.titaniumarmor.catalogo_service.model.Categoria;


@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>>{


   
    @Override
    public EntityModel<Categoria> toModel(Categoria categoria) {
        return EntityModel.of(categoria,
                linkTo(methodOn(CategoriaControllerV2.class).obtenerCategoria(categoria.getId())).withSelfRel(),
                linkTo(methodOn(CategoriaControllerV2.class).listarCategorias()).withRel("categorias"));  
            }
}
