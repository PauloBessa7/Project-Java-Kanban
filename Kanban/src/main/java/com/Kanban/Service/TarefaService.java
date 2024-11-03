package com.Kanban.Service;

import com.Kanban.Enums.TarefaPriorityEnum;
import com.Kanban.Modal.Tarefa;
import com.Kanban.Repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa insertTarefa(Tarefa tarefa){

        LocalDate now = LocalDate.now();

        if(now.isBefore(tarefa.getDueDate())){
            return tarefaRepository.save(tarefa);
        }

        throw  new RuntimeException("Problema ao criar tarefa");
    }

    public List<Tarefa> findAll() {

        List<Tarefa> todasTarefas = tarefaRepository.findAll();

        System.out.println("========= TABELA DE TAREFAS ===============");
        System.out.println("|  ID  | Data Criação     | Data Término     | Prioridade | Status       |");

        // Data atual para comparação
        LocalDate now = LocalDate.now();

        // Exibir tarefas de acordo com o status e a prioridade
        exibirTarefasPorStatusEPrioridade(todasTarefas, "A Fazer", now);
        exibirTarefasPorStatusEPrioridade(todasTarefas, "Em Progresso", now);
        exibirTarefasPorStatusEPrioridade(todasTarefas, "Concluído", now);

        return todasTarefas;
    }

    // Método auxiliar para exibir tarefas de acordo com o status e prioridade
    private void exibirTarefasPorStatusEPrioridade(List<Tarefa> tarefas, String status, LocalDate now) {
        System.out.println("Status: " + status.toUpperCase());

        // Filtra e exibe as tarefas com base no status e na prioridade
        for (TarefaPriorityEnum prioridade : TarefaPriorityEnum.values()) {
            tarefas.stream()
                    .filter(tarefa -> tarefa.getStatus().equalsIgnoreCase(status) && tarefa.getPriority() == prioridade)
                    .forEach(tarefa -> exibirTarefa(tarefa, now));
        }
    }

    // Método auxiliar para exibir uma tarefa com formatação de cores
    private void exibirTarefa(Tarefa tarefa, LocalDate now) {
        String prioridade = tarefa.getPriority().toString(); // Obtenha a prioridade da tarefa

        if (tarefa.getDueDate().isBefore(now) && !tarefa.getStatus().equalsIgnoreCase("Concluído")) {
            // Exibir em vermelho se a data de término já passou e não está "Concluído"
            System.out.printf("\033[31m|  %-4d | %-15s | %-15s | %-10s | %-12s |\033[0m%n",
                    tarefa.getId(),
                    tarefa.getCreateDate(),
                    tarefa.getDueDate(),
                    prioridade,
                    tarefa.getStatus());
        } else {
            // Exibir normalmente
            System.out.printf("|  %-4d | %-15s | %-15s | %-10s | %-12s |%n",
                    tarefa.getId(),
                    tarefa.getCreateDate(),
                    tarefa.getDueDate(),
                    prioridade,
                    tarefa.getStatus());
        }
    }

    public Tarefa selecId(int id){
        Optional<Tarefa>  tarefa = tarefaRepository.findById(id);
        if(tarefa.isEmpty()){
            throw  new RuntimeException("Tarefa nao encontrada");
        }
        return  tarefa.get();
    }

    public Tarefa deleteTarefa(int id) {
        Tarefa tarefa = selecId(id);
        if(tarefa != null) {
            tarefaRepository.deleteById(id);
            return tarefa;
        }
        throw  new RuntimeException("Tarefa nao encontrada");
    }

    public Tarefa putTarefa(Integer id, Tarefa tarefaUpdated) {
        Tarefa tarefa = selecId(id);
        if (tarefa != null) {
            LocalDate now = LocalDate.now();

            // Atualiza o título se não for nulo
            if (tarefaUpdated.getTitle() != null) {tarefa.setTitle(tarefaUpdated.getTitle());}

            // Atualiza a descrição se não for nula
            if (tarefaUpdated.getDescription() != null) {tarefa.setDescription(tarefaUpdated.getDescription());}

            // Atualiza a prioridade se não for nula
            if (tarefaUpdated.getPriority() != null) {tarefa.setPriority(tarefaUpdated.getPriority());}

            // Atualiza o status se não for nulo
            if (tarefaUpdated.getStatus() != null) {tarefa.setStatus(tarefaUpdated.getStatus());}

            // Atualiza a data de criação se não for nula e for anterior a agora
            if (tarefaUpdated.getCreateDate() != null && tarefaUpdated.getCreateDate().isBefore(now)) {tarefa.setCreateDate(tarefaUpdated.getCreateDate());}

            if (tarefaUpdated.getDueDate() != null && tarefaUpdated.getDueDate().isAfter(tarefa.getCreateDate())) {tarefa.setDueDate(tarefaUpdated.getDueDate());}

            // Salva as alterações na tarefa
            tarefaRepository.save(tarefa);
            return tarefa;
        }

        throw new RuntimeException("Tarefa não encontrada");
    }

    public Tarefa moveTarefa(Integer id){
        Tarefa tarefa = selecId(id);
        if(tarefa != null){
            switch (tarefa.getStatus()){
                case "A Fazer" :
                    tarefa.setStatus("Em Progresso");
                break;
                case "Em Progresso" :
                    tarefa.setStatus("Concluído");
                    break;
                default:
                    throw  new RuntimeException("Tarefa concluída");
            }
            return tarefaRepository.save(tarefa);
        }
        throw  new RuntimeException("Tarefa nao encontrada");
    }
}
