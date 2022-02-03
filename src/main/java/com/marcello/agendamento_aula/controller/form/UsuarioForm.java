package com.marcello.agendamento_aula.controller.form;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.marcello.agendamento_aula.controller.unum.Role;
import com.marcello.agendamento_aula.controller.unum.TipoUsuario;
import com.marcello.agendamento_aula.model.Usuario;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioForm {

  @NotBlank(message = "Campo nome é obrigatório")
  @Length(min = 5, max = 50)
  private String nome;

  @NotNull(message = "Campo data de nascimento é obrigatório")
  private LocalDate dataNascimento;

  @NotBlank(message = "Campo e-mail é obrigatório")
  @Email(message = "Campo e-mail precisa ser válido")
  @Length(min = 5, max = 50, message = "Campo e-mail precisar ter entre 5 e 50 caracteres")
  private String email;

  @NotBlank(message = "Campo senha é obrigatório")
  private String senha;

  @NotNull(message = "Campo tipo de usuário é obrigatório")
  @Enumerated(EnumType.STRING)
  private TipoUsuario tipoUsuario;

  public UsuarioForm(Usuario usuario) {
    this.setNome(usuario.getNome());
    this.setDataNascimento(usuario.getDataNascimento());
    this.setEmail(usuario.getEmail());
  }

  public Usuario converter() {
    Role role;
    
    if(this.getTipoUsuario().equals(TipoUsuario.ALUNO)) {
      role = Role.ROLE_ALUNO;
    } else {
      role = Role.ROLE_PROFESSOR;
    }

		return new Usuario(this.getNome(), this.getDataNascimento(), this.getEmail(), this.getSenha(), role);
	}
}