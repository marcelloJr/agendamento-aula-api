package com.marcello.agendamento_aula.form;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.model.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorDisciplinaForm {
  @NotNull(message = "O campo professor é obrigatório")
  @Min(value = 1, message = "O campo professor deve ser válido")
  private Long professor;

  @NotNull(message = "O campo disciplinas é obrigatório")
  private Set<Long> disciplinas;

  public Professor converter(Usuario usuario) {
		return new Professor(this, usuario);
	}
}
