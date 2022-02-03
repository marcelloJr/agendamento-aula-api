package com.marcello.agendamento_aula.controller.form;

import javax.validation.constraints.NotBlank;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthForm {
  @NotBlank(message = "O campo e-mail é obrigatório")
  private String email;

  @NotBlank(message = "O campo senha é obrigatório")
  private String senha;

  public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
}