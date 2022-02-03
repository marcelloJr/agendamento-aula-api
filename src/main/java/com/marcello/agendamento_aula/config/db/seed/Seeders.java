package com.marcello.agendamento_aula.config.db.seed;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.marcello.agendamento_aula.controller.unum.TipoUsuario;
import com.marcello.agendamento_aula.controller.form.ProfessorDisciplinaForm;
import com.marcello.agendamento_aula.controller.form.UsuarioForm;
import com.marcello.agendamento_aula.model.Disciplina;
import com.marcello.agendamento_aula.repository.DisciplinaRepository;
import com.marcello.agendamento_aula.service.ProfessorService;
import com.marcello.agendamento_aula.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Seeders {

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private ProfessorService professorService;


  @EventListener
  public void seed(ContextRefreshedEvent event) {
    this.disciplinaSeed();
    this.professorSeed();
  }

  public void disciplinaSeed() {
    if(disciplinaRepository.findAll().isEmpty()) {
      List<String> disciplinas = Arrays.asList("Língua Portuguesa / Literatura", "Redação", "Matemática", "História", 
      "Geografia", "Biologia", "Inglês", "Música", "Algoritmos e Estrutura de Dados 1", "Algoritmos e Estrutura de Dados 2", "Banco de Dados"); 
  
      disciplinaRepository.saveAll(disciplinas.stream().map(Disciplina::new).collect(Collectors.toList()));
    }
  }

  public void professorSeed() {
    if(professorService.getAll().isEmpty()) {
      List<UsuarioForm> usuarios = new ArrayList<UsuarioForm>();
      
      for(int i = 1; i <= 20; i++){
        usuarios.add(new UsuarioForm("Professor 0" + i, LocalDate.of(1970 + i, 1, i), "professor0"+ i +"@gmail.com", "professor0"+ i +"@123", TipoUsuario.PROFESSOR));
      }
  
      usuarioService.saveAll(usuarios);

      professorService.getAll().stream().forEach(v -> {
        Set<Long> disciplinas = new HashSet<Long>();

        Long disciplina01 = Math.round(Math.random() * 10) + 1;
        Long disciplina02 = Math.round(Math.random() * 10) + 1;
        
        disciplinas.add(disciplina01);
        disciplinas.add(disciplina02);

        professorService.save(new ProfessorDisciplinaForm(v.getId(), disciplinas), v.getUsuario());
      });
    }
  }
}
