package com.marcello.agendamento_aula.controller.form;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.marcello.agendamento_aula.controller.unum.StatusAgendamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoFormEdit {
  @NotNull(message = "O campo status é obrigatório")
  @Enumerated(EnumType.STRING)
  private StatusAgendamento status;

  private String observacao;
}
