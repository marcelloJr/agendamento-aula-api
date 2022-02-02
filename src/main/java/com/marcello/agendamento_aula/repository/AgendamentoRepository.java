package com.marcello.agendamento_aula.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import com.marcello.agendamento_aula.model.Agendamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
  @Query(
    "SELECT a FROM Agendamento a " +
    "WHERE a.status = 'CONFIRMADO' " +
    "AND a.data = :data " +
    "AND a.professor.id = :professor_id " +
    "AND " + 
    "(" +
    "   (a.horaInicio >= :hora_inicio AND a.horaInicio < :hora_fim)" +
    "      OR" +
    "   (a.horaFim > :hora_inicio AND a.horaFim <= :hora_fim)" +
    ")"
  )
  Optional<Agendamento> validaAgendamento(
    @Param("data") LocalDate data, 
    @Param("hora_inicio") LocalTime horaInicio,
    @Param("hora_fim") LocalTime horaFim,
    @Param("professor_id") Long professorId
  );
}
