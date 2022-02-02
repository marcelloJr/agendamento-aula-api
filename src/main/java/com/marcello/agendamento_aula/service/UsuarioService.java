package com.marcello.agendamento_aula.service;

import java.util.Optional;

import com.marcello.agendamento_aula.dto.AuthDto;
import com.marcello.agendamento_aula.form.UsuarioForm;
import com.marcello.agendamento_aula.models.Aluno;
import com.marcello.agendamento_aula.models.Professor;
import com.marcello.agendamento_aula.models.Usuario;
import com.marcello.agendamento_aula.repository.AlunoRepository;
import com.marcello.agendamento_aula.repository.ProfessorRepository;
import com.marcello.agendamento_aula.repository.UsuarioRepository;
import com.marcello.agendamento_aula.unums.TipoUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private AlunoRepository alunoRepository;  

  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  private PasswordEncoder encoder;

  public Usuario save(UsuarioForm usuarioForm) {
    usuarioForm.setSenha(encoder.encode(usuarioForm.getSenha()));

    Usuario usuario = usuarioRepository.save(usuarioForm.converter());

    if(usuarioForm.getTipoUsuario().equals(TipoUsuario.ALUNO)) {
      alunoRepository.save(new Aluno(usuario));
    } else if (usuarioForm.getTipoUsuario().equals(TipoUsuario.PROFESSOR)) {
      professorRepository.save(new Professor(usuario));
    }

    return usuario;
  }

  public Boolean validateUniqueEmail(String email) {
    return !usuarioRepository.findByEmail(email).isPresent();
  }

  public Boolean authentication(AuthDto authDto) {
    Optional<Usuario> usuario = usuarioRepository.findByEmail(authDto.getEmail());

    if(usuario.isPresent()) {
      return encoder.matches(authDto.getSenha(), usuario.get().getSenha());
    }

    return false;
  }
}
