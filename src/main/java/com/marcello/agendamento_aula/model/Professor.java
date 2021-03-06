package com.marcello.agendamento_aula.model;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.marcello.agendamento_aula.controller.dto.ProfessorDto;
import com.marcello.agendamento_aula.controller.dto.UsuarioProfessorDetailDto;
import com.marcello.agendamento_aula.controller.form.ProfessorDisciplinaForm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Professor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
  @JsonManagedReference
  private Usuario usuario;
  
  @ManyToMany
  @JoinTable(
    name = "professor_disciplina",
    joinColumns = @JoinColumn(name = "professor_id"),
    inverseJoinColumns = @JoinColumn(name = "disciplina_id")
  )
  @JsonManagedReference
  private Set<Disciplina> disciplinas;

  public Professor(Usuario usuario) {
    this.setUsuario(usuario);
  }

  public Professor(ProfessorDisciplinaForm professorDisciplinaForm, Long professorId, Usuario usuario) {
    this.setId(professorId);
    this.setUsuario(usuario);
    this.setDisciplinas(professorDisciplinaForm.getDisciplinas().stream().map(Disciplina::new).collect(Collectors.toSet()));
  }
  

  public ProfessorDto converterToDto() {
		return new ProfessorDto(this);
  }

  public UsuarioProfessorDetailDto converterToUsuarioProfessorDetailDto() {
		return new UsuarioProfessorDetailDto(this);
  }
}
