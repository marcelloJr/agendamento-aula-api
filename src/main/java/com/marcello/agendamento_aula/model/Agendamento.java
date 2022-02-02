package com.marcello.agendamento_aula.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.marcello.agendamento_aula.dto.AgendamentoDto;
import com.marcello.agendamento_aula.form.AgendamentoForm;
import com.marcello.agendamento_aula.unum.StatusAgendamento;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Agendamento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "aluno_id")
  private Aluno aluno;

  @ManyToOne
  @JoinColumn(name = "professor_id")
  private Professor professor;

  @Column(nullable = false)
  private LocalDate data;

  @Column(nullable = false)
  private LocalTime horaInicio;

  @Column(nullable = false)
  private LocalTime horaFim;
  
  @Column(length = 100)
  private String observacao;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  private StatusAgendamento status;

  public Agendamento(Aluno aluno, Professor professor, AgendamentoForm agendamentoForm, StatusAgendamento status) {
    this.setAluno(aluno);
    this.setProfessor(professor);
    this.setData(agendamentoForm.getData());
    this.setHoraInicio(agendamentoForm.getHoraInicio());
    this.setHoraFim(agendamentoForm.getHoraFim());
    this.setObservacao(agendamentoForm.getObservacao());
    this.setStatus(status);
  }

  public AgendamentoDto convertoToDto() {
    return new AgendamentoDto(this);
  }
}
