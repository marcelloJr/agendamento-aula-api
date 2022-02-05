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

import com.marcello.agendamento_aula.controller.unum.StatusAgendamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcello.agendamento_aula.controller.dto.AgendamentoDetailDto;
import com.marcello.agendamento_aula.controller.dto.AgendamentoDto;
import com.marcello.agendamento_aula.controller.form.AgendamentoForm;

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
  @JsonFormat(pattern = "HH:mm")
  private LocalTime horaInicio;

  @Column(nullable = false)
  @JsonFormat(pattern = "HH:mm")
  private LocalTime horaFim;
  
  private String observacaoProfessor;

  private String observacaoAluno;

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
    this.setObservacaoAluno(agendamentoForm.getObservacao());
    this.setHoraInicio(LocalTime.parse(agendamentoForm.getHoraInicio()));
    this.setHoraFim(LocalTime.parse(agendamentoForm.getHoraFim()));
    this.setStatus(status);
  }

  public AgendamentoDto convertoToDto() {
    return new AgendamentoDto(this);
  }

  public AgendamentoDetailDto converterToAgendamentoDetailDto() {
		return new AgendamentoDetailDto(this);
  } 
}
