package com.marcello.agendamento_aula.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.marcello.agendamento_aula.model.Agendamento;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoDetailDto {
  private Long id;
  private String professor;
  private String aluno;
  private LocalDate data;
  private LocalTime horaInicio;
  private LocalTime horaFim;
  private String observacaoProfessor;
  private String observacaoAluno;
  private String status;
  private LocalDateTime dataCriacao;
  private LocalDateTime dataAtualizacao;

  public AgendamentoDetailDto(Agendamento agendamento) {
    this.setId(agendamento.getId());
    this.setProfessor(agendamento.getProfessor().getUsuario().getNome());
    this.setAluno(agendamento.getAluno().getUsuario().getNome());
    this.setData(agendamento.getData());
    this.setHoraInicio(agendamento.getHoraInicio());
    this.setHoraFim(agendamento.getHoraFim());
    this.setObservacaoProfessor(agendamento.getObservacaoProfessor());
    this.setObservacaoAluno(agendamento.getObservacaoAluno());
    this.setStatus(agendamento.getStatus().toString());
    this.setDataCriacao(agendamento.getCreatedAt());
    this.setDataAtualizacao(agendamento.getUpdatedAt());
  }

  public static Page<AgendamentoDetailDto> convertToPage(Page<Agendamento> agendamentos) {
    return agendamentos.map(AgendamentoDetailDto::new);
  }
}
