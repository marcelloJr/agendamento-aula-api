package com.marcello.agendamento_aula.service;

import com.marcello.agendamento_aula.controller.unum.StatusAgendamento;
import com.marcello.agendamento_aula.controller.form.AgendamentoForm;
import com.marcello.agendamento_aula.model.Agendamento;
import com.marcello.agendamento_aula.model.Aluno;
import com.marcello.agendamento_aula.model.Professor;
import com.marcello.agendamento_aula.repository.AgendamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

  @Autowired
  private AgendamentoRepository agendamentoRepository;

  public Agendamento save(Aluno aluno, Professor professor, AgendamentoForm agendamentoForm) {
    return agendamentoRepository.save(new Agendamento(aluno, professor, agendamentoForm, StatusAgendamento.A_CONFIRMAR));
  }

  public Boolean hasSchedulingConflict(AgendamentoForm agendamentoForm) {
    return agendamentoRepository.validaAgendamento(agendamentoForm.getData(), agendamentoForm.getHoraInicio(), 
      agendamentoForm.getHoraFim(), agendamentoForm.getProfessor()).isPresent();
  }

}
