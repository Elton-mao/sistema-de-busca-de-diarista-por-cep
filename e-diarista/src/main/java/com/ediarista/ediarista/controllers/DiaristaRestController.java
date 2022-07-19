package com.ediarista.ediarista.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ediarista.ediarista.dtos.DiaristasPagesResponse;
import com.ediarista.ediarista.repositories.DiaristaRepository;
import com.ediarista.ediarista.services.ViacepService;

@RestController
@RequestMapping("/api/diaristas-cidade")
public class DiaristaRestController {
    @Autowired
    private DiaristaRepository repository;
    
    @Autowired
    private ViacepService viacepService;

    @GetMapping
    public DiaristasPagesResponse buscarcarDiaristasPorCep(@RequestParam String cep){
        var endereco = viacepService.buscarEnderecoPorCep(cep);
        var codigoIbge = endereco.getIbge();

        var pageable = PageRequest.of(0, 6);
        var diaristas = repository.findByCodigoIbge(codigoIbge, pageable);

        var quantidadeDiaristas = diaristas.getTotalElements() > 6 ? diaristas.getTotalElements() - 6 : 0 ;
        
        return new DiaristasPagesResponse(diaristas.getContent(),quantidadeDiaristas);
    }



}
