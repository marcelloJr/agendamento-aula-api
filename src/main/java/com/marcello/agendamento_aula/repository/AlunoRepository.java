package com.marcello.agendamento_aula.repository;

import com.marcello.agendamento_aula.models.Aluno;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
  
}
