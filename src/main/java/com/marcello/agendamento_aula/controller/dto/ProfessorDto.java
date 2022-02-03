package com.marcello.agendamento_aula.controller.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.marcello.agendamento_aula.model.Professor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorDto {
  private Long id;
  private Set<DisciplinaDto> disciplinas;

  public ProfessorDto(Professor professor) {
    this.setId(professor.getId());
    this.setDisciplinas(professor.getDisciplinas().stream().map(DisciplinaDto::new).collect(Collectors.toSet()));
  }
}
