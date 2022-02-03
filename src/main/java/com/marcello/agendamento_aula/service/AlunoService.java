package com.marcello.agendamento_aula.service;

import java.util.List;
import java.util.Optional;

import com.marcello.agendamento_aula.model.Aluno;
import com.marcello.agendamento_aula.model.Usuario;
import com.marcello.agendamento_aula.repository.AlunoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
  @Autowired
  private AlunoRepository alunoRepository;

  public Aluno save(Usuario usuario) {
    return alunoRepository.save(new Aluno(usuario));
  }

  public Optional<Aluno> getById(Long id) {
    return alunoRepository.findById(id);
  }

  public List<Aluno> getAll() {
    return alunoRepository.findAll();
  }
}
