package com.marcello.agendamento_aula.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

  @CreationTimestamp
  private LocalDateTime created_at;

  @UpdateTimestamp
  private LocalDateTime updated_at;

  public Usuario(String nome, LocalDate dataNascimento, String email, String senha) {
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.email = email;
    this.senha = senha;
  }
}
