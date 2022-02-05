package com.marcello.agendamento_aula.service;

import com.marcello.agendamento_aula.controller.unum.StatusAgendamento;

import java.util.Optional;

import com.marcello.agendamento_aula.controller.form.AgendamentoForm;
import com.marcello.agendamento_aula.model.Agendamento;
import com.marcello.agendamento_aula.model.Aluno;
import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.repository.AgendamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

  @Autowired
  private AgendamentoRepository agendamentoRepository;

  private Enum<StatusAgendamento> A_CONFIRMAR = StatusAgendamento.A_CONFIRMAR;
  private Enum<StatusAgendamento> CONFIRMADO = StatusAgendamento.CONFIRMADO;
  private Enum<StatusAgendamento> NEGADO = StatusAgendamento.NEGADO;
  private Enum<StatusAgendamento> CANCELADO = StatusAgendamento.CANCELADO;
  private Enum<StatusAgendamento> EXECUTADO = StatusAgendamento.EXECUTADO;

  public Agendamento save(Aluno aluno, Professor professor, AgendamentoForm agendamentoForm) {
    return agendamentoRepository.save(new Agendamento(aluno, professor, agendamentoForm, StatusAgendamento.A_CONFIRMAR));
  }

  public Boolean hasSchedulingConflict(AgendamentoForm agendamentoForm) {
    return agendamentoRepository.validaAgendamento(agendamentoForm.getData(), agendamentoForm.convertHoraInicio(), 
      agendamentoForm.convertHoraFim(), agendamentoForm.getProfessor()).isPresent();
  }

  public Page<Agendamento> getAllStudentSchedule(Aluno aluno, Specification<Agendamento> filters, Pageable paginacao) {
    return agendamentoRepository.findAll(Specification.where(filters).and(andStudent(aluno)), paginacao);
  }  

  public Page<Agendamento> getAllTeacherSchedule(Professor professor, Specification<Agendamento> filters, Pageable paginacao) {
    return agendamentoRepository.findAll(Specification.where(filters).and(andTeacher(professor)), paginacao);
  }

  public Optional<Agendamento> getById(Long id, Aluno aluno) {
    return agendamentoRepository.findByIdAndAluno(id, aluno);
  }

  public Optional<Agendamento> getById(Long id, Professor professor) {
    return agendamentoRepository.findByIdAndProfessor(id, professor);
  }

  public Agendamento update(Agendamento agendamento) {
    return agendamentoRepository.save(agendamento);
  }

  public Boolean validScheduleStatus(StatusAgendamento statusAtual) {
    return statusAtual.equals(A_CONFIRMAR) || statusAtual.equals(CONFIRMADO);
  }

  public Boolean validStudentScheduleStatus(StatusAgendamento statusAtual, StatusAgendamento novoStatus) {
    return (statusAtual.equals(A_CONFIRMAR) || statusAtual.equals(CONFIRMADO)) 
      && novoStatus.equals(CANCELADO);
  }

  public Boolean validTeacherScheduleStatus(StatusAgendamento statusAtual, StatusAgendamento novoStatus) {
    return 
      (statusAtual.equals(A_CONFIRMAR) && 
        (novoStatus.equals(CONFIRMADO) || novoStatus.equals(NEGADO))) ||
      (statusAtual.equals(CONFIRMADO) && 
        (novoStatus.equals(CANCELADO) || novoStatus.equals(EXECUTADO)));
  }

  private static Specification<Agendamento> andStudent(Aluno aluno) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("aluno"), aluno);
  }

  private static Specification<Agendamento> andTeacher(Professor professor) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("professor"), professor);
  }
}
