package com.ediarista.ediarista.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ediarista.ediarista.dtos.ViacepResponse;
import com.ediarista.ediarista.exceptions.CepInvalidoException;
import com.ediarista.ediarista.exceptions.CepNaoEncotradoException;

@Service
public class ViacepService {
    public ViacepResponse buscarEnderecoPorCep(String cep){
        var url = "https://viacep.com.br/ws/"+cep+"/json/";
        var clienteHttp = new RestTemplate();
        ResponseEntity <ViacepResponse>response; 
        try{
            response = clienteHttp.getForEntity(url, ViacepResponse.class);    
        }catch(HttpClientErrorException.BadRequest e){
            throw new CepInvalidoException("cep invalido");

        }
        if (response.getBody().getCep()==null) {
            throw new CepNaoEncotradoException("cep não encontrado");
        }

        return response.getBody();
    } 
}
