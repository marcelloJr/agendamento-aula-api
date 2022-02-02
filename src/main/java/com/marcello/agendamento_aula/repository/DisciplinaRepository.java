package com.marcello.agendamento_aula.repository;

import com.marcello.agendamento_aula.model.Disciplina;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
  
}
