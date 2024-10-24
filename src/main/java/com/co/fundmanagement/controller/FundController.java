package com.co.fundmanagement.controller;

import com.co.fundmanagement.model.Fund;
import com.co.fundmanagement.model.Response;
import com.co.fundmanagement.service.impl.FundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fund")
@Tag(name = "Fund", description = "Operaciones relacionadas con los fondos")
public class FundController {

    @Autowired
    private FundService fundService;

    @GetMapping("/all")
    @Operation(summary = "find all", description = "Obtener listado de todos los fondos")
    public Flux<Fund> findAll(){
        return fundService.findAll();
    }

    @GetMapping("/byId")
    @Operation(summary = "find by id", description = "Obtener fondo teniendo en cuenta el id")
    public Mono<Fund> findById(@RequestParam String id){
        return fundService.findById(id);
    }

    @PostMapping("/create")
    @Operation(summary = "Create fund", description = "Crear un nuevo fondo")
    public Mono<Response> create(@RequestBody Fund fund){
        return fundService.createFund(fund);
    }
}
