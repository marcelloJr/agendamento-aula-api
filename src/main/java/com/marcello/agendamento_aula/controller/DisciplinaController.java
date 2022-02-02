package com.marcello.agendamento_aula.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.marcello.agendamento_aula.dto.DisciplinaDto;
import com.marcello.agendamento_aula.model.Disciplina;
import com.marcello.agendamento_aula.service.DisciplinaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {
  @Autowired
  private DisciplinaService service;

  @GetMapping
	public ResponseEntity<List<DisciplinaDto>> getAll() {
		List<DisciplinaDto> disciplinas = service.getAll().stream().map(DisciplinaDto::new).collect(Collectors.toList());

    return ResponseEntity.ok(disciplinas);
	}

  @GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Optional<Disciplina> disciplina = service.getById(id);

		if (disciplina.isPresent()) {
			return ResponseEntity.ok(disciplina.get().converter());
		}
		
		return ResponseEntity.notFound().build();
	}
}
