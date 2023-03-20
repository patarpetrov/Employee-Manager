package com.example.assignmentinternshipPetarPetrov.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Employee {
    @Id
    @SequenceGenerator(name="employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    private long id;
    private String fullName;
    private String email;
    private String phoneNum;
    private String monthlySalary;
    private LocalDate dateOfBirth;
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "employee_task_table",
//            joinColumns = {
//            @JoinColumn(name="employee_id", referencedColumnName = "id")
//    }, inverseJoinColumns = {
//            @JoinColumn(name="task_id", referencedColumnName = "id")
//    })
    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToOne(mappedBy = "employee")
    private User user;

    public Employee() {
    }

    public Employee(long id, String fullName, String email, String phoneNum, String monthlySalary, LocalDate dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.monthlySalary = monthlySalary;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee(String fullName, String email, String phoneNum, String monthlySalary, LocalDate dateOfBirth) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.monthlySalary = monthlySalary;
        this.dateOfBirth = dateOfBirth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(String monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @PreRemove
    private void removeGroupsFromUsers() {
        for (Task u : tasks) {
            u.getEmployees().remove(this);
        }
    }
}
