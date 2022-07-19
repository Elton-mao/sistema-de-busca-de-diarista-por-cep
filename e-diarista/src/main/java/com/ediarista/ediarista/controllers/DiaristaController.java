package com.ediarista.ediarista.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ediarista.ediarista.models.Diarista;
import com.ediarista.ediarista.repositories.DiaristaRepository;
import com.ediarista.ediarista.services.FileService;
import com.ediarista.ediarista.services.ViacepService;
import com.ediarista.ediarista.validator.CepValidator;

@Controller
@RequestMapping("/admin/diaristas")
public class DiaristaController {
    @Autowired
    private DiaristaRepository diaristaRepository ;
    
    @Autowired
    private FileService fileService; 
    
    @Autowired
    private ViacepService viacepService;

    @Autowired 
    private CepValidator cepValidator;

    @InitBinder("diarista")
    private void initBinder(WebDataBinder binder){
        binder.addValidators(cepValidator);


    }

    @GetMapping
    public ModelAndView listardiaristas(){
        ModelAndView mView = new ModelAndView("/admin/diaristas/listar");
        mView.addObject("diaristas", diaristaRepository.findAll());
        return mView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(){
        ModelAndView mView = new ModelAndView("admin/diaristas/form");
        mView.addObject("diarista", new Diarista());
        return mView;
    }
    
    @PostMapping("/cadastrar")
    public String cadastrar(@RequestParam MultipartFile image, @Valid Diarista diarista , 
    BindingResult result) throws IOException{
        
        if(result.hasErrors()){
            return"admin/diaristas/form";
            
        }
        var filename = fileService.salvar(image);
        diarista.setFoto(filename);
        
        var cep = diarista.getCep();

        var endereco = viacepService.buscarEnderecoPorCep(cep);

        var codigoIbge = endereco.getIbge();

        diarista.setCodigoIbge(codigoIbge);

        diaristaRepository.save(diarista);
        return "redirect:/admin/diaristas";

    }

    @GetMapping("/{id}/detalhes")
    public ModelAndView detalhes(@PathVariable Long id){
        ModelAndView mView = new ModelAndView("admin/diaristas/detalhes"); 
        diaristaRepository.getOne(id);
        return mView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id){
        ModelAndView mView = new ModelAndView("admin/diaristas/form");
        mView.addObject("diarista", diaristaRepository.getById(id));
        return mView;
    }
    @PostMapping("/{id}/editar")
    public String editar(@RequestParam MultipartFile image ,@PathVariable Long id,@Valid Diarista diarista ,BindingResult result) throws IOException{
        if(result.hasErrors()){
            return"admin/diaristas/form";
            
        }
        var diaristaAtual = diaristaRepository.getById(id);
        if (image.isEmpty()) {
            diarista.setFoto(diaristaAtual.getFoto());
        
        }
        else{
            var filename =fileService.salvar(image);
            diarista.setFoto(filename);    
        }
        diaristaRepository.save(diarista);
        return "redirect:/admin/diaristas";

    }
    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id){
        diaristaRepository.deleteById(id);
        return "redirect:/admin/diaristas";

}

}
