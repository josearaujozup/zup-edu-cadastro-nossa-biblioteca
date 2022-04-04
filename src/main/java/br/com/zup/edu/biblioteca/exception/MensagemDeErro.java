package br.com.zup.edu.biblioteca.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MensagemDeErro {
	@JsonProperty
    private List<String> messages=new ArrayList<>();
	
	public void adicionarErrorPeloFieldError(FieldError fieldError){
        messages.add(String.format("O %s %s",fieldError.getField(),fieldError.getDefaultMessage()));
    }
	
    public void adicionarError(String campo,String defaultMessage){
        messages.add(String.format("O %s %s",campo,defaultMessage));
    }
	
}
