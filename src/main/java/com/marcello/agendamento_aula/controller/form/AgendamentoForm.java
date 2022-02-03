package com.marcello.agendamento_aula.controller.form;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(
    dataType = "String", 
    example = "13:00", 
    value = "Horário de inicio da aula", 
    required = true
  )
  @NotNull(message = "O campo hora de inicio é obrigatório")
  private LocalTime horaInicio;

  @ApiModelProperty(
    dataType = "String", 
    example = "14:30", 
    value = "Horário de fim da aula",
    required = true
  )
  @NotNull(message = "O campo hora do fim é obrigatório")
  private LocalTime horaFim;

  private String observacao;
}
