package com.ediarista.ediarista.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ediarista.ediarista.models.Diarista;
import com.ediarista.ediarista.services.ViacepService;

@Component
public class CepValidator implements Validator{
    @Autowired
   private ViacepService viacepService;

    @Override
    public boolean supports(Class<?> clazz) {
       return Diarista.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var diarista =(Diarista) target;
        try{
            var cep = diarista.getCep();
            viacepService.buscarEnderecoPorCep(cep);
        } catch (RuntimeException e){
            errors.rejectValue("cep", null,e.getMessage());

        }
    }

    
}
