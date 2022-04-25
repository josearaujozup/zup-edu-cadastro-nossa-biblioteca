package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Livro;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class LivroRequest {
    @NotBlank
    @Length(max = 200)
    private String titulo;

    @NotBlank
    @Length(max = 2000)
    private String descricao;

    @NotBlank
    @ISBN(type = ISBN.Type.ANY)
    private String isbn;

    @PastOrPresent
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPublicacao;


    public LivroRequest(String titulo, String descricao, String isbn, LocalDate dataPublicacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
    }

    public LivroRequest() {
    }

    public Livro paraLivro(){
        return new Livro(titulo,descricao,isbn,dataPublicacao);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }
}
