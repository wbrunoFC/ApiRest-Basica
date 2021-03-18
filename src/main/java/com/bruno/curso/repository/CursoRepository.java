package com.bruno.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno.forum.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	Curso findByNome(String nomeCurso);

}
