package com.marcello.agendamento_aula.repository;

import java.util.Optional;

import com.marcello.agendamento_aula.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  Optional<Usuario> findByEmail(String email);

  Optional<Usuario> findByEmailAndSenha(String email, String senha);
  
}