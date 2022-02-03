package com.marcello.agendamento_aula.controller;

import java.net.URI;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.marcello.agendamento_aula.controller.dto.AgendamentoDto;
import com.marcello.agendamento_aula.controller.dto.MensagemErroDto;
import com.marcello.agendamento_aula.controller.form.AgendamentoForm;
import com.marcello.agendamento_aula.model.Aluno;
import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.service.AgendamentoService;
import com.marcello.agendamento_aula.service.AlunoService;
import com.marcello.agendamento_aula.service.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
  private AlunoService alunoService;

  @Autowired
  private ProfessorService professorService;

  @PostMapping
  @Transactional
  public ResponseEntity<?> saveSchedule(@RequestBody @Valid AgendamentoForm payload, UriComponentsBuilder uriBuilder) {
    Optional<Aluno> aluno = alunoService.getById(payload.getAluno());

    if(aluno.isPresent()){
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

        AgendamentoDto agendamentoDto = service.save(aluno.get(), professor.get(), payload).convertoToDto();

        URI uri = uriBuilder.path("/agendamento/{id}").buildAndExpand(agendamentoDto.getId()).toUri();
        return ResponseEntity.created(uri).body(agendamentoDto);
      }

      return ResponseEntity.badRequest().body(new MensagemErroDto("O campo professor deve ser válido"));
    }
    
    return ResponseEntity.badRequest().body(new MensagemErroDto("O campo aluno deve ser válido"));    
  }
}
