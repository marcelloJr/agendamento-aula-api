package com.marcello.agendamento_aula.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthDto {
  @NotBlank(message = "E-mail {is.required}")
  private String email;

  @NotBlank(message = "Senha {is.required}")
  private String senha;
}