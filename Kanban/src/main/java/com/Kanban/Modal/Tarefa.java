package com.Kanban.Modal;

import com.Kanban.Enums.TarefaPriorityEnum;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(nullable = false)
    private String title;

    private String description;
    private LocalDate createDate = LocalDate.now(); // Data de criação automática
    private String status = "A Fazer";
    private TarefaPriorityEnum priority;
    private LocalDate dueDate; // Data de vencimento definida pelo usuário




    // Getters
    public int getId() {
        return id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TarefaPriorityEnum getPriority() {
        return priority;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(TarefaPriorityEnum priority) {
        this.priority = priority;
    }

    public void setCreateDate(LocalDate createDate) {this.createDate = createDate;}
}
