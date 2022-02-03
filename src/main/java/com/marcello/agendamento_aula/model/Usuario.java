package com.marcello.agendamento_aula.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.marcello.agendamento_aula.controller.dto.UsuarioDetailDto;
import com.marcello.agendamento_aula.controller.dto.UsuarioDto;
import com.marcello.agendamento_aula.controller.unum.Role;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements UserDetails {

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
  @JsonBackReference
  private Aluno aluno;

  @OneToOne(mappedBy = "usuario")
  @JsonBackReference
  private Professor professor;

  @Enumerated(EnumType.STRING)
  private Role perfil;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Usuario(String nome, LocalDate dataNascimento, String email, String senha, Role perfil) {
    this.setNome(nome);
    this.setDataNascimento(dataNascimento);
    this.setEmail(email);
    this.setSenha(senha);
    this.setPerfil(perfil);
  }

  public UsuarioDto converterDto() {
    return new UsuarioDto(this);
  }

  public UsuarioDetailDto converterDetailDto() {
    return new UsuarioDetailDto(this);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return this.getSenha();
  }

  @Override
  public String getUsername() {
    return this.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
