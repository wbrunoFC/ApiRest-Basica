package com.bruno.forum.modelo.controller.form;

import com.bruno.curso.repository.TopicosRepository;
import com.bruno.forum.modelo.Topico;
import com.sun.istack.NotNull;

public class AtualizacaoTopicoForm {

	@NotNull
	private String titulo;
	@NotNull
	private String mensagem;
	
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
	public Topico atualizar(Long id, TopicosRepository topicosRepository) {
		Topico topico = topicosRepository.getOne(id);
		topico.setTitulo(titulo);
		topico.setMensagem(mensagem);
		return topico;
	}
	
	
}
