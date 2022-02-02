package com.marcello.agendamento_aula.service;

import java.util.List;
import java.util.Optional;

import com.marcello.agendamento_aula.dto.ProfessorDto;
import com.marcello.agendamento_aula.form.ProfessorDisciplinaForm;
import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.model.Usuario;
import com.marcello.agendamento_aula.repository.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {
  @Autowired
  private ProfessorRepository professorRepository;

  public ProfessorDto save(ProfessorDisciplinaForm professorDisciplinaForm, Usuario usuario) {
    return professorRepository.save(professorDisciplinaForm.converter(usuario)).converter();
  }

  public List<Professor> getAll() {
    return professorRepository.findAll();
  }

  public Optional<Professor> getById(Long id) {
    return professorRepository.findById(id);
  }

}
