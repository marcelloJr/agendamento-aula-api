package com.marcello.agendamento_aula.controller;

import java.net.URI;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.marcello.agendamento_aula.controller.dto.AgendamentoDto;
import com.marcello.agendamento_aula.controller.dto.AgendamentoGetAllDto;
import com.marcello.agendamento_aula.controller.dto.MensagemErroDto;
import com.marcello.agendamento_aula.controller.form.AgendamentoForm;
import com.marcello.agendamento_aula.controller.unum.Role;
import com.marcello.agendamento_aula.model.Agendamento;
import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.model.Usuario;
import com.marcello.agendamento_aula.service.AgendamentoService;
import com.marcello.agendamento_aula.service.ProfessorService;
import com.marcello.agendamento_aula.service.security.CurrentUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {
  
  @Autowired
  private AgendamentoService service;

  @Autowired
  private ProfessorService professorService;

  @PreAuthorize("hasRole('ALUNO')")
  @PostMapping
  @Transactional
  public ResponseEntity<?> saveSchedule(
    @RequestBody @Valid AgendamentoForm payload, 
    UriComponentsBuilder uriBuilder,
    @CurrentUser Usuario usuarioLogado
  ) {

    Optional<Professor> professor = professorService.getById(payload.getProfessor());

    if(professor.isPresent()){
      long validacaoHora = payload.getHoraInicio().until(payload.getHoraFim(), MINUTES);
      
      if(validacaoHora < 0){
        return ResponseEntity.badRequest().body(new MensagemErroDto("A hora de início não pode ser menor que a do fim"));
      } else if(validacaoHora < 30) {
        return ResponseEntity.badRequest().body(new MensagemErroDto("A duração mínima da aula deve ser de 30 minutos"));
      } else if(service.hasSchedulingConflict(payload)){
        return ResponseEntity.badRequest().body(new MensagemErroDto("Já existe um agendamento para o horário informado"));
      }

      AgendamentoDto agendamentoDto = service.save(usuarioLogado.getAluno(), professor.get(), payload).convertoToDto();

      URI uri = uriBuilder.path("/agendamento/{id}").buildAndExpand(agendamentoDto.getId()).toUri();
      return ResponseEntity.created(uri).body(agendamentoDto);
    }

    return ResponseEntity.badRequest().body(new MensagemErroDto("O campo professor deve ser válido"));
  }

  @GetMapping
  public Page<AgendamentoGetAllDto> getSchedule(
    @CurrentUser Usuario usuarioLogado, 
    @PageableDefault(page = 0, size = 10) 
    @SortDefaults({
      @SortDefault(sort = "data", direction = Direction.DESC),
      @SortDefault(sort = "horaInicio", direction = Direction.ASC)
    }) Pageable paginacao
  ) {

    Page<Agendamento> agendamentos;

    if(usuarioLogado.getPerfil().equals(Role.ROLE_ALUNO)) {
      agendamentos = service.getAllStudentSchedule(usuarioLogado.getAluno(), paginacao);
    } else {
      agendamentos = service.getAllTeacherSchedule(usuarioLogado.getProfessor(), paginacao);
    }
    
    return AgendamentoGetAllDto.convertToPage(agendamentos);
  }
}
