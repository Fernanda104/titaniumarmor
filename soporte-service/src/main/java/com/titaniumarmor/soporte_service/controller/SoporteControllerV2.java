package com.titaniumarmor.soporte_service.controller;

import com.titaniumarmor.soporte_service.assembler.SoporteModelAssembler;
import com.titaniumarmor.soporte_service.model.Soporte;
import com.titaniumarmor.soporte_service.service.SoporteService;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/soporte/v2")
public class SoporteControllerV2 {

    private final SoporteService soporteService;
    private final SoporteModelAssembler assembler;

    private static final Logger logger =
            LoggerFactory.getLogger(SoporteControllerV2.class);

    public SoporteControllerV2(
            SoporteService soporteService,
            SoporteModelAssembler assembler
    ) {
        this.soporteService = soporteService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Soporte>> listarSoportes() {
        logger.info("V2 GET /soporte - Listando soportes");

        List<EntityModel<Soporte>> soportes =
                soporteService.listar()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                soportes,
                linkTo(
                        methodOn(SoporteControllerV2.class)
                                .listarSoportes()
                ).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Soporte> obtenerSoporte(
            @PathVariable Long id
    ) {
        logger.info(
                "V2 GET /soporte/{} - Obteniendo soporte",
                id
        );

        Soporte soporte =
                soporteService.buscarPorId(id);

        return assembler.toModel(soporte);
    }
}