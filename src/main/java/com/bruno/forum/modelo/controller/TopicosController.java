package com.bruno.forum.modelo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bruno.curso.repository.CursoRepository;
import com.bruno.curso.repository.TopicosRepository;
import com.bruno.forum.modelo.Topico;
import com.bruno.forum.modelo.controller.dto.TopicoDto;
import com.bruno.forum.modelo.controller.dto.DetalhesDoTopicoDto;
import com.bruno.forum.modelo.controller.form.AtualizacaoTopicoForm;
import com.bruno.forum.modelo.controller.form.TopicoForm;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	/* @ResponseBody Indicar ao Spring que o retorno do método deve ser devolvido como resposta
	 * @RestController substitui o @ResponseBody pq ja vem como padrao na anotation */
	
	@Autowired
	private TopicosRepository topicosRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
		if (nomeCurso == null) {
			List<Topico> topicos = topicosRepository.findAll();
			return TopicoDto.convert(topicos);
		} else {
			List<Topico> topicos = topicosRepository.findByCurso_Nome(nomeCurso);
			return TopicoDto.convert(topicos);
		}
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Validated TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicosRepository.save(topico);
		
		URI uri  = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
		
		/* Testar na ferramenta PostMan: [POST] | http://localhost:8080/topicos 
		 * Selecione o input raw 
		 * Headers -> Content-Type |[VALUE] application/json
		 * 
		 * {
		 * 		"titulo":"Duvida Postman"
		 * 		"mensagem": "Texto da mensagem"
		 * 		"nomeCurso": "Javao"
		 * }
		 * 
		 * */
	
	}
	
	@GetMapping("/{id}")
	public void  detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicosRepository.findById(id);
		
		validarId(topico);
		
	}

	private ResponseEntity<DetalhesDoTopicoDto> validarId(Optional<Topico> topico) {
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Validated AtualizacaoTopicoForm form){
		Optional<Topico> topico = topicosRepository.findById(id);

		validarId(topico);
		
		Topico topico2 = form.atualizar(id, topicosRepository);
		
		return ResponseEntity.ok(new TopicoDto(topico2));
		
		/* Testar na ferramenta PostMan: [PUT] | http://localhost:8080/topicos 
		 * Selecione o input raw 
		 * Headers -> [KEY] Content-Type |[VALUE] application/json
		 * 
		 * {
		 * 		"titulo":"Duvida atual"
		 * 		"mensagem": "Texto da mensagem atual"
		 * 		"nomeCurso": "Javao atual"
		 * }
		 * 
		 * */
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> topico = topicosRepository.findById(id);

		validarId(topico);
		
		topicosRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}