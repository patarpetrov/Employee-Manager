package com.example.employeeManager.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
public class Task {
    @Id
    @SequenceGenerator(name="task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    //@ManyToMany(mappedBy = "tasks", fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name="task_employee_table", joinColumns = {
            @JoinColumn(name="task_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name="employee_id", referencedColumnName = "id")
    })
    public Set<Employee> employees;

    public Task(){

    }

    public Task(int id, String title, String description, LocalDate dueDate, Set<Employee> employees) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.employees = employees;
    }

    public Task(String title, String description, LocalDate dueDate, Set<Employee> employees) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", employees=" + employees +
                '}';
    }
}
