package com.marcello.agendamento_aula.middleware;

public class ValidationDto {
  private String campo;
	private String mensagem;
	
	public ValidationDto(String campo, String mensagem) {
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public String getMensagem() {
		return mensagem;
	}
}
