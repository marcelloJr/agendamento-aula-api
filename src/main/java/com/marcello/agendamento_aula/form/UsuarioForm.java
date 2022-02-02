package com.marcello.agendamento_aula.form;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.marcello.agendamento_aula.model.Usuario;
import com.marcello.agendamento_aula.unum.TipoUsuario;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioForm {

  @NotBlank(message = "Campo nome é obrigatório")
  @Length(min = 5, max = 50)
  private String nome;

  @NotNull(message = "Campo data de nascimento é obrigatório")
  private LocalDate dataNascimento;

  @NotBlank(message = "Campo e-mail é obrigatório")
  @Email(message = "Campo e-mail precisa ser válido")
  @Length(min = 5, max = 50)
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
		return new Usuario(this.getNome(), this.getDataNascimento(), this.getEmail(), this.getSenha());
	}
}
