package com.marcello.agendamento_aula.form;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.marcello.agendamento_aula.models.Usuario;
import com.marcello.agendamento_aula.unums.TipoUsuario;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioForm {

  @NotBlank(message = "Nome {not.blank}")
  @Length(min = 5, max = 50)
  private String nome;

  @NotNull(message = "Data de nascimento {not.blank}")
  private LocalDate dataNascimento;

  @NotBlank(message = "E-mail {not.blank}")
  @Email(message = "E-mail {not.valid}")
  @Length(min = 5, max = 50)
  private String email;

  @NotBlank(message = "Senha {not.blank}")
  private String senha;

  @NotNull(message = "Tipo de usuário {not.blank}")
  @Enumerated(EnumType.STRING)
  private TipoUsuario tipoUsuario;

  public UsuarioForm(Usuario usuario) {
    this.setNome(usuario.getNome());
    this.setDataNascimento(usuario.getDataNascimento());
    this.setEmail(usuario.getEmail());
  }

  public Usuario converter() {
		return new Usuario(this.getNome(), this.getDataNascimento(), this.getEmail(), this.getSenha());
	}
}
