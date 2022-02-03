package com.marcello.agendamento_aula.controller.dto;

import java.time.LocalDate;

import com.marcello.agendamento_aula.model.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
  private String nome;
  private LocalDate dataNascimento;
  private String email;

  public UsuarioDto(Usuario usuario) {
    this.setNome(usuario.getNome());
    this.setDataNascimento(usuario.getDataNascimento());
    this.setEmail(usuario.getEmail());
  }
}
