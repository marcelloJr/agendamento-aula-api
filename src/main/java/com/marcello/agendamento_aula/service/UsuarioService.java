package com.marcello.agendamento_aula.service;

import java.util.List;
import java.util.Optional;

import com.marcello.agendamento_aula.controller.unum.TipoUsuario;
import com.marcello.agendamento_aula.controller.form.AuthForm;
import com.marcello.agendamento_aula.controller.form.UsuarioForm;
import com.marcello.agendamento_aula.model.Aluno;
import com.marcello.agendamento_aula.model.Usuario;
import com.marcello.agendamento_aula.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
  
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private AlunoService alunoService;  

  @Autowired
  private ProfessorService professorService;

  @Autowired
  private PasswordEncoder encoder;

  public Usuario save(UsuarioForm usuarioForm) {
    usuarioForm.setSenha(encoder.encode(usuarioForm.getSenha()));

    Usuario usuario = usuarioRepository.save(usuarioForm.converter());

    if(usuarioForm.getTipoUsuario().equals(TipoUsuario.ALUNO)) {
      alunoService.save(usuario);
    } else if (usuarioForm.getTipoUsuario().equals(TipoUsuario.PROFESSOR)) {
      professorService.save(usuario);
    }

    return usuario;
  }

  public void saveAll(List<UsuarioForm> usuarioForm) {
    usuarioForm.stream().forEach(v -> {
      this.save(v);
    });
  }

  public Boolean validateUniqueEmail(String email) {
    return !usuarioRepository.findByEmail(email).isPresent();
  }

  public Boolean authentication(AuthForm authDto) {
    Optional<Usuario> usuario = usuarioRepository.findByEmail(authDto.getEmail());

    if(usuario.isPresent()) {
      return encoder.matches(authDto.getSenha(), usuario.get().getSenha());
    }

    return false;
  }

  public Usuario getById(Long id) {
    return usuarioRepository.getById(id);
  }
}
