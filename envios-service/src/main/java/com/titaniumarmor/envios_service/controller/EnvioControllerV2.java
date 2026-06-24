package com.titaniumarmor.envios_service.controller;

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

import com.titaniumarmor.envios_service.assembler.EnvioModelAssembler;
import com.titaniumarmor.envios_service.model.Envio;
import com.titaniumarmor.envios_service.service.EnviosService;








@RestController
@RequestMapping("envios/v2")
public class EnvioControllerV2 {
    private final EnviosService enviosService;
    private final EnvioModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(EnvioControllerV2.class);

    public EnvioControllerV2(EnviosService enviosService, EnvioModelAssembler assembler) {
        this.enviosService = enviosService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Envio>> listarEnvios() {
        logger.info("V2 GET /envios - Listando envios");
        List<EntityModel<Envio>> envios = enviosService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(envios, linkTo(methodOn(EnvioControllerV2.class).listarEnvios()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Envio> obtenerEnvio(@PathVariable Long id) {
        logger.info("V2 GET /envios/{} - Obteniendo envio", id);
        Envio envio = enviosService.buscarPorId(id);
        return assembler.toModel(envio);
    }
}
