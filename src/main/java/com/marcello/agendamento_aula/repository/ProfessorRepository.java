package com.marcello.agendamento_aula.repository;

import com.marcello.agendamento_aula.models.Professor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
  
}