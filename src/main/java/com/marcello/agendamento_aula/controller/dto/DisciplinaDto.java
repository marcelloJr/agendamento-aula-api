package com.marcello.agendamento_aula.controller.dto;

import com.marcello.agendamento_aula.model.Disciplina;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisciplinaDto {
  private Long id;
  private String nome;

  public DisciplinaDto(Disciplina disciplina) {
    this.setId(disciplina.getId());
    this.setNome(disciplina.getNome());
  }
}
