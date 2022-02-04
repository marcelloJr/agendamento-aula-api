package com.marcello.agendamento_aula.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.marcello.agendamento_aula.controller.dto.MensagemErroDto;
import com.marcello.agendamento_aula.controller.dto.ProfessorDto;
import com.marcello.agendamento_aula.controller.dto.UsuarioProfessorDetailDto;
import com.marcello.agendamento_aula.controller.dto.UsuarioProfessorDto;
import com.marcello.agendamento_aula.controller.form.ProfessorDisciplinaForm;
import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.model.Usuario;
import com.marcello.agendamento_aula.service.DisciplinaService;
import com.marcello.agendamento_aula.service.ProfessorService;
import com.marcello.agendamento_aula.service.security.CurrentUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
  @Autowired
  private ProfessorService service;

  @Autowired
  private DisciplinaService disciplinaService;

  @PreAuthorize("hasRole('ALUNO')")
  @GetMapping
	public Page<UsuarioProfessorDto> getAll(
    @RequestParam(required = false) String nome,
    @RequestParam(required = false) List<Long> disciplinas,
    @PageableDefault(sort = "usuario.nome", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao
  ) {
    
    Page<Professor> professores = service.getAll(nome, disciplinas, paginacao);

    return UsuarioProfessorDto.convertToPage(professores);
	}

  @PreAuthorize("hasRole('ALUNO')")
  @GetMapping("/{id}")
	public ResponseEntity<UsuarioProfessorDetailDto> getById(@PathVariable Long id) {

    Optional<Professor> professor = service.getById(id);

		if (professor.isPresent()) {
			return ResponseEntity.ok(professor.get().converterToUsuarioProfessorDetailDto());
		}
		
		return ResponseEntity.notFound().build();
	}

  @PreAuthorize("hasRole('PROFESSOR')")
  @PostMapping(value = "/disciplinas")
  @Transactional
  public ResponseEntity<?> saveSubjects(
    @RequestBody @Valid ProfessorDisciplinaForm payload, 
    UriComponentsBuilder uriBuilder, 
    @CurrentUser Usuario usuarioLogado
  ) {
    
    Professor professor = service.getTeacherByUser(usuarioLogado.getId());
    Long subjectId = disciplinaService.validateSubjects(payload.getDisciplinas());
    
    if(subjectId > 0) {
      return ResponseEntity.badRequest().body(new MensagemErroDto("Disciplina com id " + subjectId + " não existe"));  
    }

    ProfessorDto professorDto = service.save(payload, professor.getId(), professor.getUsuario());
    URI uri = uriBuilder.path("/professor/disciplinas/{id}").buildAndExpand(professorDto.getId()).toUri();

    return ResponseEntity.created(uri).body(professorDto);
  }
}
