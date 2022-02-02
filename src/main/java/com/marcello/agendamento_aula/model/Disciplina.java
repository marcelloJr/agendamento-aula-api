package com.marcello.agendamento_aula.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.marcello.agendamento_aula.dto.DisciplinaDto;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Disciplina {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(length = 50, unique = true)
  private String nome;

  @CreationTimestamp
  private LocalDateTime created_at;

  @UpdateTimestamp
  private LocalDateTime updated_at;

  @ManyToMany(mappedBy = "disciplinas")
  @JsonBackReference
  private Set<Professor> professores;

  public Disciplina(String nome) {
    this.setNome(nome);
  }

  public DisciplinaDto converter() {
    return new DisciplinaDto(this);
  }

  public Disciplina(Long id) {
    this.setId(id);
  }
}
