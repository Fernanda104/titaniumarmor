package com.titaniumarmor.ventas_service.controller;

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

import com.titaniumarmor.ventas_service.assembler.VentaModelAssembler;
import com.titaniumarmor.ventas_service.model.Venta;
import com.titaniumarmor.ventas_service.service.VentaService;







@RestController
@RequestMapping("ventas/v2")
public class VentaControllerV2 {
    private final VentaService ventaService;
    private final VentaModelAssembler assembler;
    private static final Logger logger = LoggerFactory.getLogger(VentaControllerV2.class);

    public VentaControllerV2(VentaService ventaService, VentaModelAssembler assembler) {
        this.ventaService = ventaService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Venta>> listarVentas() {
        logger.info("V2 GET /ventas - Listando ventas");
        List<EntityModel<Venta>> ventas = ventaService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(ventas, linkTo(methodOn(VentaControllerV2.class).listarVentas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Venta> obtenerVenta(@PathVariable Long id) {
        logger.info("V2 GET /ventas/{} - Obteniendo ventas", id);
        Venta venta = ventaService.buscarPorId(id);
        return assembler.toModel(venta);
    }
}
