package com.marcello.agendamento_aula.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MensagemErroDto {
  private String mensagem;

  public MensagemErroDto(String mensagem) {
    this.setMensagem(mensagem);
  }
}
