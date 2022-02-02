package com.marcello.agendamento_aula.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.marcello.agendamento_aula.dto.AuthDto;
import com.marcello.agendamento_aula.dto.MensagemErroDto;
import com.marcello.agendamento_aula.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

  @Autowired
  private UsuarioService service;

  @PostMapping
  @Transactional
  public ResponseEntity<?> authentication(@RequestBody @Valid AuthDto authDto) {
    if(service.authentication(authDto)) {
      return ResponseEntity.ok().body(new MensagemErroDto("Usuário autenticado com sucesso"));
    }
    return ResponseEntity.badRequest().body(new MensagemErroDto("Usuário/senha inválido"));
  }
  
}
