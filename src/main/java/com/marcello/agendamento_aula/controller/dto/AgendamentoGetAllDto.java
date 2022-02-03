package com.marcello.agendamento_aula.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.marcello.agendamento_aula.model.Agendamento;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoGetAllDto {
  private Long id;
  private String professor;
  private String aluno;
  private LocalDate data;
  private LocalTime horaInicio;
  private LocalTime horaFim;
  private String observacao;
  private String status;

  public AgendamentoGetAllDto(Agendamento agendamento) {
    this.setId(agendamento.getId());
    this.setProfessor(agendamento.getProfessor().getUsuario().getNome());
    this.setAluno(agendamento.getAluno().getUsuario().getNome());
    this.setData(agendamento.getData());
    this.setHoraInicio(agendamento.getHoraInicio());
    this.setHoraFim(agendamento.getHoraFim());
    this.setObservacao(agendamento.getObservacao());
    this.setStatus(agendamento.getStatus().toString());
  }

  public static Page<AgendamentoGetAllDto> convertToPage(Page<Agendamento> agendamentos) {
    return agendamentos.map(AgendamentoGetAllDto::new);
  }
}
