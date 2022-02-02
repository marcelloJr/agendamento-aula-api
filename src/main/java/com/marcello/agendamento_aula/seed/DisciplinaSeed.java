package com.marcello.agendamento_aula.seed;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.marcello.agendamento_aula.model.Disciplina;
import com.marcello.agendamento_aula.repository.DisciplinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaSeed {
  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @EventListener
  public void seed(ContextRefreshedEvent event) {
    seed();
  }

  private void seed() {
    if(disciplinaRepository.findAll().isEmpty()) {
      List<String> disciplinas = Arrays.asList("Língua Portuguesa / Literatura", "Redação", "Matemática", "História", 
      "Geografia", "Biologia", "Inglês", "Música", "Algoritmos e Estrutura de Dados 1", "Algoritmos e Estrutura de Dados 2", "Banco de Dados"); 
  
      disciplinaRepository.saveAll(disciplinas.stream().map(Disciplina::new).collect(Collectors.toList()));
    }
  }
   
}
