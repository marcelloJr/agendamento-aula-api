package com.marcello.agendamento_aula.controller.form;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoForm {
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
  @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Horário de início inválida")
  private String horaInicio;

  @ApiModelProperty(
    dataType = "String", 
    example = "14:30", 
    value = "Horário de fim da aula",
    required = true
  )
  @NotNull(message = "O campo hora do fim é obrigatório")
  @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Horário de fim inválida")
  private String horaFim;

  private String observacao;

  public LocalTime convertHoraInicio() {
    return LocalTime.parse(this.getHoraInicio());
  }

  public LocalTime convertHoraFim() {
    return LocalTime.parse(this.getHoraFim());
  }
}
