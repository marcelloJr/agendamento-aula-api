package com.marcello.agendamento_aula.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.marcello.agendamento_aula.model.Disciplina;
import com.marcello.agendamento_aula.repository.DisciplinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  public List<Disciplina> getAll() {
    return disciplinaRepository.findAll();
  }

  public Optional<Disciplina> getById(Long id) {
    return disciplinaRepository.findById(id);
  }

  public Long validateSubjects(Set<Long> disciplinas) {
    List<Long> disciplinasList = new ArrayList<>(disciplinas);

    for(int i = 0; i < disciplinasList.size(); i++) {
      if(!this.getById(disciplinasList.get(i)).isPresent()){
        return disciplinasList.get(i);
      }
    }

    return 0l;
  }

}
