package com.Kanban.Controller;


import com.Kanban.Modal.Tarefa;
import com.Kanban.Service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/tasks")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> getAllTarefas() {
        return tarefaService.findAll();
    }


    @PostMapping // criar tarefa
    public Tarefa postTarefa(@RequestBody  Tarefa tarefa){
        return tarefaService.insertTarefa(tarefa);
    }

    @DeleteMapping("/{id}") // deletar tarefa
    public Tarefa deleteTarefa(@PathVariable Integer id) {
        if(tarefaService.selecId(id)!=null){
            return tarefaService.deleteTarefa(id);
        }
        return tarefaService.selecId(id);
    }

    @PutMapping("/{id}") // atualizar tarefa
    public Tarefa putTarefa(@PathVariable Integer id,  @RequestBody Tarefa tarefa){
        if(tarefaService.selecId(id)!=null){
            return tarefaService.putTarefa(id,tarefa);
        }
        return tarefaService.selecId(id);
    }

    @PutMapping("/{id}/move") // atualizar status tarefa
    public Tarefa moveTarefa(@PathVariable Integer id){
        if(tarefaService.selecId(id)!=null){
            return tarefaService.moveTarefa(id);
        }
        return tarefaService.selecId(id);
    }


}
