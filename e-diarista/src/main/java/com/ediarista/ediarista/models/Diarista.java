package com.ediarista.ediarista.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.ediarista.ediarista.controllers.FileController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Diarista {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotNull
    @Size(min = 3,max = 100)
    @Column(nullable = false , length = 100) 
    @JsonProperty("nome_completo")
    private String nomeCompleto; 

    @NotNull
    @Size(min = 11,max= 14)
    @CPF
    @Column(nullable = false, length = 14, unique = true)
    @JsonIgnore
    private String cpf; 

    @NotNull
    @NotEmpty
    @Email
    @Column(nullable = false,unique = true)
    @JsonIgnore
    private String email;

    @NotNull
    @Size(min = 11, max = 15)
    @Column(nullable = false,unique = true,length = 15)
    @JsonIgnore
    private String telefone;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    @JsonIgnore
    private String logradouro;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    @JsonIgnore 
    private String numero; 

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    @JsonIgnore
    private String bairro ;

    @Column(nullable = true)
    @JsonIgnore
    private String complemento;
    
    @NotNull
    @Size(min = 8 ,max = 9)
    @Column(nullable = false)
    @JsonIgnore
    private String cep; 
   
    
    @Column(nullable = false)
    @JsonIgnore
    private String codigoIbge;
   
    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String cidade ; 
   
    @NotNull
    @NotEmpty
    @Size(min = 2,max=2)
    @Column(nullable = false,length = 2)
    @JsonIgnore
    private String estado; 

    @JsonIgnore
    @Column(nullable = false)
    private String foto;

    public String getFotoUrl() throws IOException{
        return linkTo(WebMvcLinkBuilder.methodOn(FileController.class).
        file(this.foto)).toString();
    }
    
}
