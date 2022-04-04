package br.com.zup.edu.biblioteca.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Exemplar {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(optional = false)
	private Livro livro;
	
	private LocalDateTime criadoEm=LocalDateTime.now();
	
	public Exemplar(Livro livro) {
		this.livro = livro;
	}
	
	/**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Exemplar() {
    	
    }

	public Long getId() {
		return id;
	}
 
}
