package com.marcello.agendamento_aula.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.marcello.agendamento_aula.dto.MensagemErroDto;
import com.marcello.agendamento_aula.dto.ProfessorDto;
import com.marcello.agendamento_aula.dto.UsuarioProfessorDetailDto;
import com.marcello.agendamento_aula.dto.UsuarioProfessorDto;
import com.marcello.agendamento_aula.form.ProfessorDisciplinaForm;
import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.service.DisciplinaService;
import com.marcello.agendamento_aula.service.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
  @Autowired
  private ProfessorService service;

  @Autowired
  private DisciplinaService disciplinaService;

  @PostMapping(value = "/disciplinas")
  @Transactional
  public ResponseEntity<?> saveSubjects(@RequestBody @Valid ProfessorDisciplinaForm payload, UriComponentsBuilder uriBuilder) {
    Optional<Professor> professorValido = service.getById(payload.getProfessor());

    if(professorValido.isPresent()){
      Long subjectId = disciplinaService.validateSubjects(payload.getDisciplinas());
      
      if(subjectId > 0) {
        return ResponseEntity.badRequest().body(new MensagemErroDto("Disciplina com id " + subjectId + " não existe"));  
      }

      ProfessorDto professorDto = service.save(payload, professorValido.get().getUsuario());

      URI uri = uriBuilder.path("/professor/disciplinas/{id}").buildAndExpand(professorDto.getId()).toUri();
      return ResponseEntity.created(uri).body(professorDto);

    }

    return ResponseEntity.badRequest().body(new MensagemErroDto("O campo professor deve ser válido"));    
  }

  @GetMapping
	public ResponseEntity<List<UsuarioProfessorDto>> getAll() {
		List<UsuarioProfessorDto> professores = service.getAll().stream().map(UsuarioProfessorDto::new).collect(Collectors.toList());
    return ResponseEntity.ok(professores);
	}

  @GetMapping("/{id}")
	public ResponseEntity<UsuarioProfessorDetailDto> getById(@PathVariable Long id) {
    Optional<Professor> professor = service.getById(id);

		if (professor.isPresent()) {
			return ResponseEntity.ok(professor.get().converterToUsuarioProfessorDetailDto());
		}
		
		return ResponseEntity.notFound().build();
	}
}
