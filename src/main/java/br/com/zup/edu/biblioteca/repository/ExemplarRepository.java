package br.com.zup.edu.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.biblioteca.model.Exemplar;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long>{

}
