package com.marcello.agendamento_aula.dto;

import com.marcello.agendamento_aula.model.Professor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioProfessorDto {
  private Long id;
  private String nome;
  private String email;

  public UsuarioProfessorDto(Professor professor) {
    this.setNome(professor.getUsuario().getNome());
    this.setEmail(professor.getUsuario().getEmail());
    this.setId(professor.getId());
  }
}
