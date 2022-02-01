package com.marcello.agendamento_aula.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String nome;

  @Column(name = "data_nascimento")
  private LocalDate dataNascimento;

  @Column(length = 50, unique = true)
  private String email;

  private String senha;

  @OneToOne(mappedBy = "usuario")
  private Aluno aluno;

  @OneToOne(mappedBy = "usuario")
  private Professor professor;
}
