package com.marcello.agendamento_aula.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.marcello.agendamento_aula.controller.unum.Role;
import com.marcello.agendamento_aula.model.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDetailDto {
  private String nome;
  private LocalDate dataNascimento;
  private String email;
  private Role perfil;
  private LocalDateTime dataRegistro;

  public UsuarioDetailDto(Usuario usuario) {
    this.setNome(usuario.getNome());
    this.setDataNascimento(usuario.getDataNascimento());
    this.setEmail(usuario.getEmail());
    this.setPerfil(usuario.getPerfil());
    this.setDataRegistro(usuario.getCreatedAt());
  }
}
