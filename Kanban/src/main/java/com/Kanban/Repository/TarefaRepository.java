package com.Kanban.Repository;

import com.Kanban.Modal.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TarefaRepository extends JpaRepository<Tarefa, Integer> { }
