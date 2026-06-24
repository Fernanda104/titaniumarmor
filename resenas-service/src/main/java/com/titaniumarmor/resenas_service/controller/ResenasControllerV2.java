package com.titaniumarmor.resenas_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.titaniumarmor.resenas_service.assembler.ResenaModelAssembler;
import com.titaniumarmor.resenas_service.model.Resena;
import com.titaniumarmor.resenas_service.service.ResenasService;



@RestController
@RequestMapping("resenas/v2")
public class ResenasControllerV2 {
    private final ResenasService resenasService;
    private final ResenaModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(ResenasControllerV2.class);

    public ResenasControllerV2(ResenasService resenasService, ResenaModelAssembler assembler) {
        this.resenasService = resenasService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Resena>> listarResenas() {
        logger.info("V2 GET /resenas - Listando resenas");
        List<EntityModel<Resena>> resenas = resenasService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(resenas, linkTo(methodOn(ResenasControllerV2.class).listarResenas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Resena> obtenerResena(@PathVariable Long id) {
        logger.info("V2 GET /resenas/{} - Obteniendo usuario", id);
        Resena resena = resenasService.buscarPorId(id);
        return assembler.toModel(resena);
    }
}