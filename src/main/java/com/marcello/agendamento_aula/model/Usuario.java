package com.marcello.agendamento_aula.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.marcello.agendamento_aula.dto.UsuarioDto;

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

  @Column(length = 50, nullable = false)
  private String nome;

  @Column(name = "data_nascimento", nullable = false)
  private LocalDate dataNascimento;

  @Column(length = 50, unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String senha;

  @OneToOne(mappedBy = "usuario")
  private Aluno aluno;

  @OneToOne(mappedBy = "usuario")
  @JsonBackReference
  private Professor professor;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Usuario(String nome, LocalDate dataNascimento, String email, String senha) {
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.email = email;
    this.senha = senha;
  }

  public UsuarioDto converterDto() {
    return new UsuarioDto(this);
  }
}
