package com.titaniumarmor.promociones_service.controller;

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

import com.titaniumarmor.promociones_service.assembler.PromocionModelAssembler;
import com.titaniumarmor.promociones_service.model.Promocion;
import com.titaniumarmor.promociones_service.service.PromocionService;



@RestController
@RequestMapping("promociones/v2")
public class PromocionControllerV2 {
    private final PromocionService promocionService;
    private final PromocionModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(PromocionControllerV2.class);

    public PromocionControllerV2(PromocionService promocionService, PromocionModelAssembler assembler) {
        this.promocionService = promocionService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Promocion>> listarPromociones() {
        logger.info("V2 GET /promociones - Listando promociones");
        List<EntityModel<Promocion>> promociones = promocionService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(promociones, linkTo(methodOn(PromocionControllerV2.class).listarPromociones()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Promocion> obtenerPromocion(@PathVariable Long id) {
        logger.info("V2 GET /promociones/{} - Obteniendo promocion", id);
        Promocion promocion = promocionService.buscarPorId(id);
        return assembler.toModel(promocion);
    }
}