package com.marcello.agendamento_aula.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.marcello.agendamento_aula.model.Professor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioProfessorDetailDto {
  private Long id;
  private String nome;
  private String email;
  private Set<DisciplinaDto> disciplinas;

  public UsuarioProfessorDetailDto(Professor professor) {
    this.setNome(professor.getUsuario().getNome());
    this.setEmail(professor.getUsuario().getEmail());
    this.setId(professor.getId());
    this.setDisciplinas(professor.getDisciplinas().stream().map(DisciplinaDto::new).collect(Collectors.toSet()));
  }
}
