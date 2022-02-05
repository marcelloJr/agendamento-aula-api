package com.marcello.agendamento_aula.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.marcello.agendamento_aula.model.Agendamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoDto {
  private Long id;
  private String professor;
  private LocalDate data;
  private LocalTime horaInicio;
  private LocalTime horaFim;
  private String observacao;

  public AgendamentoDto(Agendamento agendamento) {
    this.setProfessor(agendamento.getProfessor().getUsuario().getNome());
    this.setData(agendamento.getData());
    this.setHoraInicio(agendamento.getHoraInicio());
    this.setHoraFim(agendamento.getHoraFim());
    this.setObservacao(agendamento.getObservacaoAluno());
    this.setId(agendamento.getId());
  }
}
