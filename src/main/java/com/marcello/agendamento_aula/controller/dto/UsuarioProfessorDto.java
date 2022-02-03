package com.marcello.agendamento_aula.controller.dto;

import com.marcello.agendamento_aula.model.Professor;

import org.springframework.data.domain.Page;

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
  
  public static Page<UsuarioProfessorDto> convertToPage(Page<Professor> professores) {
    return professores.map(UsuarioProfessorDto::new);
  }
}
