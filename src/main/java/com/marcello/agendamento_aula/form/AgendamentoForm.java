package com.marcello.agendamento_aula.form;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoForm {
  @NotNull(message = "O campo aluno é obrigatório")
  private Long aluno;

  @NotNull(message = "O campo professor é obrigatório")
  private Long professor;

  @NotNull(message = "O campo data é obrigatório")
  private LocalDate data;

  @NotNull(message = "O campo hora de inicio é obrigatório")
  private LocalTime horaInicio;

  @NotNull(message = "O campo hora do fim é obrigatório")
  private LocalTime horaFim;

  private String observacao;
}
