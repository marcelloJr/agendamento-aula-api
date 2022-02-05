package com.marcello.agendamento_aula.config.db.seed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.marcello.agendamento_aula.controller.form.ProfessorDisciplinaForm;
import com.marcello.agendamento_aula.controller.form.UsuarioForm;
import com.marcello.agendamento_aula.model.Disciplina;
import com.marcello.agendamento_aula.repository.DisciplinaRepository;
import com.marcello.agendamento_aula.service.AlunoService;
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

  @Autowired
  private AlunoService alunoService;


  @EventListener
  public void seed(ContextRefreshedEvent event) {
    this.disciplinaSeed();
    this.alunoSeed();
    this.professorSeed();
  }

  public void disciplinaSeed() {
    if(disciplinaRepository.findAll().isEmpty()) {
      List<String> disciplinas = Arrays.asList("Língua Portuguesa / Literatura", "Redação", "Matemática", "História", 
      "Geografia", "Biologia", "Inglês", "Música", "Algoritmos e Estrutura de Dados 1", "Algoritmos e Estrutura de Dados 2", "Banco de Dados"); 
  
      disciplinaRepository.saveAll(disciplinas.stream().map(Disciplina::new).collect(Collectors.toList()));
    }
  }

  public void alunoSeed() {
    if(alunoService.getAll().isEmpty()) {
      usuarioService.save(new UsuarioForm("Aluno 01", "2000-10-10", "aluno01@gmail.com", "aluno01@123", "ALUNO"));
    }
  }

  public void professorSeed() {
    if(professorService.getAll().isEmpty()) {
      List<UsuarioForm> professores = new ArrayList<UsuarioForm>();
      
      for(int i = 1; i <= 20; i++){
        professores.add(new UsuarioForm("Professor 0" + i, "1970-01-01", "professor0"+ i +"@gmail.com", "professor0"+ i +"@123", "PROFESSOR"));
      }
  
      usuarioService.saveAll(professores);

      professorService.getAll().stream().forEach(professor -> {
        Set<Long> disciplinas = new HashSet<Long>();

        Long disciplina01 = Math.round(Math.random() * 10) + 1;
        Long disciplina02 = Math.round(Math.random() * 10) + 1;
        
        disciplinas.add(disciplina01);
        disciplinas.add(disciplina02);

        professorService.save(new ProfessorDisciplinaForm(disciplinas), professor.getId(), professor.getUsuario());
      });
    }
  }
}
