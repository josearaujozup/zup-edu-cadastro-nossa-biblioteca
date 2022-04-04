package br.com.zup.edu.biblioteca.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.biblioteca.model.Exemplar;
import br.com.zup.edu.biblioteca.model.Livro;
import br.com.zup.edu.biblioteca.repository.ExemplarRepository;
import br.com.zup.edu.biblioteca.repository.LivroRepository;

@RestController
@RequestMapping("/livros/{isbn}/exemplares")
public class CadastrarNovoExemplarController {
	
	private final LivroRepository livroRepository;
	private final ExemplarRepository repository;
	
	public CadastrarNovoExemplarController(LivroRepository livroRepository, ExemplarRepository repository) {
		this.livroRepository = livroRepository;
		this.repository = repository;
	}
	
	@PostMapping
	public ResponseEntity<Void> cadastrar(@PathVariable String isbn, UriComponentsBuilder uriComponentsBuilder){
		
		Livro livro = livroRepository.findByISBN(isbn).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe cadastro de livro para o isbn informado"));
		
		Exemplar novoExemplar = new Exemplar(livro);
		//comentario
		repository.save(novoExemplar);
		URI location = uriComponentsBuilder.path("/livros/{isbn}/exemplares/{id}").buildAndExpand(livro.getId(),novoExemplar.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
}
