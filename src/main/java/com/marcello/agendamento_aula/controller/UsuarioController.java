package com.marcello.agendamento_aula.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.marcello.agendamento_aula.dto.MensagemErroDto;
import com.marcello.agendamento_aula.form.UsuarioForm;
import com.marcello.agendamento_aula.model.Usuario;
import com.marcello.agendamento_aula.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  private UsuarioService service;

  @PostMapping(produces = "application/json")
  @Transactional
  public ResponseEntity<?> save(@RequestBody @Valid UsuarioForm payload, UriComponentsBuilder uriBuilder) {
    if(service.validateUniqueEmail(payload.getEmail())) {
      Usuario usuario = service.save(payload);
      URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();

      return ResponseEntity.created(uri).body(usuario.converterDto()); 
    }

    return ResponseEntity.badRequest().body(new MensagemErroDto("Email já cadastrado"));
  }
}
