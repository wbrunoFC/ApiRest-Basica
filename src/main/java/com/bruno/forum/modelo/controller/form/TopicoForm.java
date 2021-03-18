package com.bruno.forum.modelo.controller.form;

import com.bruno.curso.repository.CursoRepository;
import com.bruno.forum.modelo.Curso;
import com.bruno.forum.modelo.Topico;
import com.sun.istack.NotNull;

public class TopicoForm {
	
	@NotNull
	private String titulo;
	@NotNull
	private String mensagem;
	@NotNull
	private String nomeCurso;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public Topico converter(CursoRepository cursoRepository) {
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo,mensagem, curso);
	}
	
	
}
