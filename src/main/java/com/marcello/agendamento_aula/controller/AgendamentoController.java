package com.marcello.agendamento_aula.controller;

import java.net.URI;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.marcello.agendamento_aula.controller.dto.AgendamentoDto;
import com.marcello.agendamento_aula.controller.dto.AgendamentoDetailDto;
import com.marcello.agendamento_aula.controller.dto.MensagemErroDto;
import com.marcello.agendamento_aula.controller.form.AgendamentoForm;
import com.marcello.agendamento_aula.controller.form.AgendamentoFormEdit;
import com.marcello.agendamento_aula.controller.unum.Role;
import com.marcello.agendamento_aula.controller.unum.StatusAgendamento;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public Page<AgendamentoDetailDto> getSchedules(
    @CurrentUser Usuario usuarioLogado, 
    @PageableDefault(page = 0, size = 10) 
    @SortDefaults({
      @SortDefault(sort = "data", direction = Direction.ASC),
      @SortDefault(sort = "horaInicio", direction = Direction.ASC)
    }) Pageable paginacao
  ) {

    Page<Agendamento> agendamentos;

    if(usuarioLogado.getPerfil().equals(Role.ROLE_ALUNO)) {
      agendamentos = service.getAllStudentSchedule(usuarioLogado.getAluno(), paginacao);
    } else {
      agendamentos = service.getAllTeacherSchedule(usuarioLogado.getProfessor(), paginacao);
    }
    
    return AgendamentoDetailDto.convertToPage(agendamentos);
  }

  @GetMapping("/{id}")
	public ResponseEntity<AgendamentoDetailDto> getById(@CurrentUser Usuario usuarioLogado, @PathVariable Long id) {

    Optional<Agendamento> agendamento = null;

    if(usuarioLogado.getPerfil().equals(Role.ROLE_ALUNO)) {
      agendamento = service.getById(id, usuarioLogado.getAluno());
    } else {
      agendamento = service.getById(id, usuarioLogado.getProfessor());
    }
    
		if (agendamento != null && agendamento.isPresent()) {
      return ResponseEntity.ok(agendamento.get().converterToAgendamentoDetailDto());
    }
		
		return ResponseEntity.notFound().build();
	}

  @PatchMapping("/{id}")
	public ResponseEntity<?> updateSchedule(
    @CurrentUser Usuario usuarioLogado, 
    @PathVariable Long id, 
    @RequestBody @Valid AgendamentoFormEdit payload
  ) {

    Optional<Agendamento> agendamento = null;

    Boolean usuarioAluno = usuarioLogado.getPerfil().equals(Role.ROLE_ALUNO);
    Boolean usuarioProfessor = usuarioLogado.getPerfil().equals(Role.ROLE_PROFESSOR);

    if(usuarioAluno) {      
      agendamento = service.getById(id, usuarioLogado.getAluno());
    } else {
      agendamento = service.getById(id, usuarioLogado.getProfessor());
    }
    
		if (agendamento != null && agendamento.isPresent()) {

      StatusAgendamento statusAtual = agendamento.get().getStatus();
      StatusAgendamento novoStatus = payload.getStatus();

      if(service.validScheduleStatus(statusAtual)) {
        
        if(!statusAtual.equals(novoStatus)){
          
          if(usuarioAluno && !service.validStudentScheduleStatus(statusAtual, novoStatus)) {
            return ResponseEntity.badRequest().body(new MensagemErroDto("Operação não permitida para alunos"));
          } else if(usuarioProfessor && !service.validTeacherScheduleStatus(statusAtual, novoStatus)) {
            return ResponseEntity.badRequest().body(new MensagemErroDto("Operação não permitida"));
          }
          
          if(usuarioAluno) {      
            agendamento.get().setObservacaoAluno(payload.getObservacao());
          } else {
            agendamento.get().setObservacaoProfessor(payload.getObservacao());
          }

          agendamento.get().setStatus(novoStatus);

          service.update(agendamento.get());

          return ResponseEntity.ok(agendamento.get().converterToAgendamentoDetailDto());

        } else {
          return ResponseEntity.badRequest().body(new MensagemErroDto("Agendamento já se encontra no status informado"));
        }
      } else {
        return ResponseEntity.badRequest().body(new MensagemErroDto("Não é possivel alterar um agendamento que foi nagado, cancelado ou executado"));
      }
    }

		return ResponseEntity.notFound().build();
	}
}
