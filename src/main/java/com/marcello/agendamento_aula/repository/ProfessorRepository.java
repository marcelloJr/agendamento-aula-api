package com.marcello.agendamento_aula.repository;

import java.util.List;

import com.marcello.agendamento_aula.model.Professor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, JpaSpecificationExecutor<Professor> {
  Page<Professor> findByUsuarioNomeContains(String usuarioNome, Pageable paginacao);

  Page<Professor> findByDisciplinasIdIn(List<Long> disciplinas, Pageable paginacao);

  Page<Professor> findByUsuarioNomeContainsAndDisciplinasIdIn(String usuarioNome, List<Long> disciplinas, Pageable paginacao);

  Professor findByUsuarioId(Long id);
}
