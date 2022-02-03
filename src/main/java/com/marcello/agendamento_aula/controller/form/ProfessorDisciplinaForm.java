package com.marcello.agendamento_aula.controller.form;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDisciplinaForm {
  @NotNull(message = "O campo disciplinas é obrigatório")
  private Set<Long> disciplinas;

  public Professor converter(Long professorId, Usuario usuario) {
		return new Professor(this, professorId, usuario);
	}
}
