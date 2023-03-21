package com.example.assignmentinternshipPetarPetrov.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Department {
    @Id
    @SequenceGenerator(name="department_sequence", sequenceName = "department_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
    private long id;

    private String name;
    @OneToMany(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "employee_id")
    private List<Employee> employeeList;

    public Department(String name, List<Employee> employeeList) {
        this.name = name;
        this.employeeList = employeeList;
    }
}
