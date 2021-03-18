package com.bruno.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno.forum.modelo.Topico;

public interface TopicosRepository extends JpaRepository<Topico, Long>{
	
	/*Para resolver problemas de ambiguidade e configurar corretamente os relacionamento usa [nome]_[nome] */
	List<Topico> findByCurso_Nome(String nomeCurso);

}
