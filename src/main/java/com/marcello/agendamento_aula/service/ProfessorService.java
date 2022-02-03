package com.marcello.agendamento_aula.service;

import java.util.List;
import java.util.Optional;

import com.marcello.agendamento_aula.controller.dto.ProfessorDto;
import com.marcello.agendamento_aula.controller.form.ProfessorDisciplinaForm;
import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.model.Usuario;
import com.marcello.agendamento_aula.repository.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {
  
  @Autowired
  private ProfessorRepository professorRepository;

  public ProfessorDto save(ProfessorDisciplinaForm professorDisciplinaForm, Usuario usuario) {
    return professorRepository.save(professorDisciplinaForm.converter(usuario)).converterToDto();
  }

  public Professor save(Usuario usuario) {
    return professorRepository.save(new Professor(usuario));
  }

  public Page<Professor> getAll(String nome, List<Long> disciplinas, Pageable paginacao) {
    if(nome != null && disciplinas == null) {
      return professorRepository.findByUsuarioNomeContains(nome, paginacao);
    }

    if(nome == null && disciplinas != null) {
      return professorRepository.findByDisciplinasIdIn(disciplinas, paginacao);
    }

    if(nome != null && disciplinas != null) {
      return professorRepository.findByUsuarioNomeContainsAndDisciplinasIdIn(nome, disciplinas, paginacao);
    }

    return professorRepository.findAll(paginacao);
  }

  public List<Professor> getAll() {
    return professorRepository.findAll();
  }

  public Optional<Professor> getById(Long id) {
    return professorRepository.findById(id);
  }

}
