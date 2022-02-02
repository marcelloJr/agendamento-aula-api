package com.marcello.agendamento_aula.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthDto {
  @NotBlank(message = "O campo e-mail é obrigatório")
  private String email;

  @NotBlank(message = "O campo senha é obrigatório")
  private String senha;
}